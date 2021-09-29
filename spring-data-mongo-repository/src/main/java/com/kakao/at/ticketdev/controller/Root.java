package com.kakao.at.ticketdev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class Root {
	@GetMapping(value = "/index")
	public String index() {
		return "index";
	}
}
