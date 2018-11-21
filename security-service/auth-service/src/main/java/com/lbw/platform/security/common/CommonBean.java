package com.lbw.platform.security.common;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lbw.platform.security.validateCode.JdbcValidateCodeRepository;
import com.lbw.platform.security.validateCode.ValidateCodeRepository;


@Configuration
//这么 写防止出现循环引用
public class CommonBean {
	@Value("${tokenStore.driverClassname}")
	private String driverName;
	
	@Value("${tokenStore.url}")
	private String url;
	
	@Value("${tokenStore.userName}")
	private String userName;
	
	@Value("${tokenStore.password}")
	private String password;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new DomainUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ValidateCodeRepository jdbcValidateCodeRepository() {
		return new JdbcValidateCodeRepository();
	}
	
	@Bean
	public DataSource jdbcTokenDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverName);//"com.mysql.jdbc.Driver"
		dataSource.setUrl(url);//"jdbc:mysql://localhost:3306/auth"
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}

}
