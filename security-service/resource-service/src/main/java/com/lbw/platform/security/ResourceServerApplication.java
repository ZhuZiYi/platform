package com.lbw.platform.security;

/***
 * 注意：其他工程要引入资源服务器,其main()必须在与资源服务器同一级包名下
 */


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lbw.platform.security.config.SecurityProperties;



@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.class)
public class ResourceServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ResourceServerApplication.class, args);
	}

}
