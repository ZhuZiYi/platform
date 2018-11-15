package com.lbw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lbw.domain.SysAuthority;
import com.lbw.domain.SysRole;
import com.lbw.domain.SysUser;
import com.lbw.security.SecurityUtils;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServerApplication {

	@Bean(name = "auditorAware")
	public AuditorAware<String> auditorAware() {
		return () -> SecurityUtils.getCurrentUserUsername();
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}



}
