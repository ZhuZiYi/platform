package com.lbw.platform.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	@GetMapping("/")
	public String login(){
		log.info("登录成功,跳转主页面");
		return "index";
	}
	
	@RequestMapping("/login")
	public String loginPage(){
		return "login";
	}
}
