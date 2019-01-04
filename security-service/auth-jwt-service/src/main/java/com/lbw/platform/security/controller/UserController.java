package com.lbw.platform.security.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@RequestMapping("/hello")
	public String hello() {

		return "hello";
	}

	@RequestMapping("/admin")
	public String admin(Principal principal) {

		return principal.getName();
	}
}
