package com.lbw.platform.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lbw.platform.*")
@MapperScan("com.lbw.platform.admin.*")
public class AdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
}
