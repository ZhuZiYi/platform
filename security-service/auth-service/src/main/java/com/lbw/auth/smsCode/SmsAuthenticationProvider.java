package com.lbw.auth.smsCode;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsAuthenticationProvider implements AuthenticationProvider {
	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
		UserDetails userDetails = userDetailsService.loadUserByUsername((String) smsAuthenticationToken.getPrincipal());

		if (userDetails == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}

		SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails,
				userDetails.getAuthorities());

		authenticationResult.setDetails(smsAuthenticationToken.getDetails());
		return authenticationResult;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return SmsAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
