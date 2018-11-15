package com.lbw.auth.smsCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.lbw.validateCode.ValidateCodeProcessHalder;

@Component("smsAuthenticationConfig")
public class SmsAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	@Autowired
    private AuthenticationSuccessHandler qhauthenticationSuccessHadler;

    @Autowired
    private AuthenticationFailureHandler qhAuthenticationFailureHandler;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ValidateCodeProcessHalder validateCodeProcessHalder;

	@Override
	public void configure(HttpSecurity http) {
		SmsCodeAuthenticationFilter smsAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		smsAuthenticationFilter.setAuthenticationSuccessHandler(qhauthenticationSuccessHadler);
		smsAuthenticationFilter.setAuthenticationFailureHandler(qhAuthenticationFailureHandler);
		smsAuthenticationFilter.setValidateCodeProcessHalder(validateCodeProcessHalder);
		SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
		smsAuthenticationProvider.setUserDetailsService(userDetailsService);
		http.authenticationProvider(smsAuthenticationProvider).addFilterAfter(smsAuthenticationFilter,
				UsernamePasswordAuthenticationFilter.class);
	}
}
