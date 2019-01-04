package com.lbw.platform.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.lbw.platform.security.config.SecurityConfig.RestAuthenticationEntryPoint;
import com.lbw.platform.security.filter.JWTAuthenticationFilter;
import com.lbw.platform.security.filter.JWTLoginFilter;
import com.lbw.platform.security.impl.CustomAuthenticationProvider;
import com.lbw.platform.security.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	@ConditionalOnMissingBean(PasswordEncoder.class)
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		// auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		// 使用自定义身份验证组件
		auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 禁用 csrf
		http.cors().and().csrf().disable().authorizeRequests()
				// 允许以下请求
				.antMatchers("/hello").permitAll()
				// 所有请求需要身份认证
				.anyRequest().authenticated().and()
				// 验证登陆
				.addFilter(new JWTLoginFilter(authenticationManager()))
				// 验证token
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				//认证不通过后的处理
		        .exceptionHandling()
		        .authenticationEntryPoint(new RestAuthenticationEntryPoint());
	}

	
	/**
	 * 权限不通过的处理
	 */
	public static class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
			// TODO Auto-generated method stub
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
	                "Authentication Failed: " + authException.getMessage());
		}
	}

}
