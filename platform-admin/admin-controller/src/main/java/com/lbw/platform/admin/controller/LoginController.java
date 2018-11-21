package com.lbw.platform.admin.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	@GetMapping("/")
	public String login(Model model){
		log.info("登录成功,跳转主页面");
		model.addAttribute("user",
				((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		return "index";
	}
	
	@RequestMapping("/login")
	public String loginPage(){
		return "login";
	}
}
