package com.lbw.platform.security.auth.imageCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import com.lbw.platform.security.validateCode.ValidateCodeProcessHalder;

@Component("imageCodeAuthenticationConfig")
public class ImageCodeAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ValidateCodeProcessHalder validateCodeProcessHalder;

	@Autowired
    private AuthenticationSuccessHandler qhauthenticationSuccessHadler;

    @Autowired
    private AuthenticationFailureHandler qhAuthenticationFailureHandler;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void configure(HttpSecurity http) {
		ImageCodeAuthenticationFilter imageCodeAuthenticationFilter = new ImageCodeAuthenticationFilter();
		imageCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		imageCodeAuthenticationFilter.setAuthenticationSuccessHandler(qhauthenticationSuccessHadler);
		imageCodeAuthenticationFilter.setAuthenticationFailureHandler(qhAuthenticationFailureHandler);
		imageCodeAuthenticationFilter.setValidateCodeProcessHalder(validateCodeProcessHalder);
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		http.authenticationProvider(daoAuthenticationProvider).addFilterBefore(imageCodeAuthenticationFilter,
				AbstractPreAuthenticatedProcessingFilter.class);//UsernamePasswordAuthenticationFilter
	}
}
