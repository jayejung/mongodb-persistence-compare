package com.kakao.at.ticketdev.controller.user;

import com.kakao.at.ticketdev.user.EnumUtils;
import com.kakao.at.ticketdev.user.EnumValue;
import com.kakao.at.ticketdev.user.UserType;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
	@Qualifier(value = "mongoTestDbTemplate")
	MongoTemplate mongoTemplate;

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
		JSONObject document = new JSONObject();
		document.put("_id", id);
		document.put("passwd", passwd);
		document.put("userType", userType);

		JSONObject result = mongoTemplate.insert(document, "userCol");

		if (result != null) {
			resultStatus = 1;
		}
		model.addAttribute("status", resultStatus);

		return "/user/regProc";
	}

	@GetMapping(value = "list")
	public String list(@RequestParam @Nullable String userType, Model model) {
		// db.userCol.find({}, {"userType": 1})
		Query query = new Query();
		// criteria
		if (StringUtils.hasText(userType)) {
			query.addCriteria(Criteria.where("userType").is(userType));
		}
		// projection
		query.fields().include("userType");
		List<JSONObject> userList = mongoTemplate.find(query, JSONObject.class, "userCol");

		model.addAttribute("result", userList);

		List<EnumValue> userTypeList = EnumUtils.toValues(UserType.class);
		model.addAttribute("userTypeList", userTypeList);

		model.addAttribute("userType", userType);
		return "/user/list";
	}
}
