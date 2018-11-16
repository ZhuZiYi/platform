package com.lbw.platform.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;


@Configuration
//这么 写防止出现循环引用
public class CommonBean {
	@Value("${tokenStore.checkTokenUrl}")
	private String checkTokenUrl;

	@Primary
	@Bean
	public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(checkTokenUrl);//"http://localhost:8000/oauth/check_token"
        tokenService.setClientId("hxw");
        tokenService.setClientSecret("hxwSecret");
        return tokenService;
    }
}
