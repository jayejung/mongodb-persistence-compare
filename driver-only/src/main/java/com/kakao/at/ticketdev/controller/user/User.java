package com.kakao.at.ticketdev.controller.user;

import com.kakao.at.ticketdev.appsevices.MongoRepoService;
import com.kakao.at.ticketdev.user.enumcode.EnumUtils;
import com.kakao.at.ticketdev.user.enumcode.EnumValue;
import com.kakao.at.ticketdev.user.enumcode.UserType;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "user")
public class User {
	@Resource
	MongoRepoService mongoRepoService;

	@GetMapping(value = "regForm")
	public String regForm(Model model) {

		List<EnumValue> userTypeList = EnumUtils.toValues(UserType.class);

		model.addAttribute("userTypeList", userTypeList);
		return "/user/regForm";
	}

	@PostMapping(value = "regProc",
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String regProc(@RequestBody MultiValueMap<String, String> formData, Model model) {

		String id = formData.getFirst("id");
		String passwd = formData.getFirst("passwd");
		String userType = formData.getFirst("userType");

		// db.userCol.insertOne({"_id": "aaa", "passwd": "aaa", "userType": "Admin"});
		JSONObject document = new JSONObject();
		document.put("_id", id);
		document.put("passwd", passwd);
		document.put("userType", userType);

		JSONObject queryObject = new JSONObject();
		queryObject.put("action", "insert");
		queryObject.put("collection", "userCol");
		queryObject.put("document", document);

		JSONObject result = mongoRepoService.query(queryObject);
		model.addAttribute("result", result);

		return "user/regProc";
	}

	@GetMapping(value = "list")
	public String list(@RequestParam @Nullable String userType, Model model) {
		// db.userCol.find({}, {"userType": 1})
		JSONObject criteria = new JSONObject();
		if (StringUtils.hasText(userType)) {
			criteria.put("userType", userType);
		}
		JSONObject projection = new JSONObject();
		projection.put("userType", 1);
		JSONObject queryObject = new JSONObject();
		queryObject.put("action", "find");
		queryObject.put("collection", "userCol");
		queryObject.put("criteria", criteria);
		queryObject.put("projection", projection);

		JSONObject result = mongoRepoService.query(queryObject);

		model.addAttribute("result", result);

		List<EnumValue> userTypeList = EnumUtils.toValues(UserType.class);
		model.addAttribute("userTypeList", userTypeList);

		model.addAttribute("userType", userType);

		return "/user/list";
	}
}
