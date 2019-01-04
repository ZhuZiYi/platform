package com.lbw.platform.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lbw.platform.*")
public class JwtAuthenticationServer {
	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationServer.class, args);
	}
}
