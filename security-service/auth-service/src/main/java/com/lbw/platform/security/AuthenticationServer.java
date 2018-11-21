package com.lbw.platform.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lbw.platform.*")
//@MapperScan("com.lbw.platform.authentication.mapper")
public class AuthenticationServer {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServer.class, args);
	}
}
