package com.kakao.at.ticketdev.controller.user;

import com.kakao.at.ticketdev.repository.UserRepository;
import com.kakao.at.ticketdev.user.EnumUtils;
import com.kakao.at.ticketdev.user.EnumValue;
import com.kakao.at.ticketdev.user.UserEntity;
import com.kakao.at.ticketdev.user.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "user")
public class User {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "regForm")
    public String regForm(Model model) {
        List<EnumValue> userTypeList = EnumUtils.toValues(UserType.class);

        model.addAttribute("userTypeList", userTypeList);
        return "/user/regForm";
    }

    @PostMapping(value = "regProc",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String regProc(@RequestBody MultiValueMap<String, String> formData, Model model) {
        int resultStatus = 0;
        String id = formData.getFirst("id");
        String passwd = formData.getFirst("passwd");
        String userType = formData.getFirst("userType");

        // db.userCol.insertOne({"_id": "aaa", "passwd": "aaa", "userType": "Admin"});
        UserEntity userEntity = new UserEntity(id, passwd, userType);
        UserEntity result = userRepository.insert(userEntity);

        if (result != null) {
            resultStatus = 1;
        }
        model.addAttribute("status", resultStatus);

        return "/user/regProc";
    }

    @GetMapping(value = "list")
    public String list(@RequestParam @Nullable String userType, Model model) {
        List<UserEntity> userList;

        //db.userCol.find({}, {"userType": 1})
        if (StringUtils.hasText(userType)) {
            userList = userRepository.findByUserType(userType);
        } else {
            userList = userRepository.findAll();
        }

        model.addAttribute("result", userList);

        List<EnumValue> userTypeList = EnumUtils.toValues(UserType.class);
        model.addAttribute("userTypeList", userTypeList);

        model.addAttribute("userType", userType);
        return "/user/list";
    }
}
