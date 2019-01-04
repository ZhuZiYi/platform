package com.lbw.platform.security.impl;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import com.lbw.platform.security.exception.UsernameIsExitedException;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	private UserDetailsService userDetailsService;

	private PasswordEncoder passwordEncoder;

	public CustomAuthenticationProvider(UserDetailsService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		// 获取认证的用户名 & 密码
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		// 通过用户名从数据库中查询该用户
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		// 判断密码(这里是md5加密方式)是否正确
		String dbPassword = userDetails.getPassword();
		//String encoderPassword = DigestUtils.md5DigestAsHex(password.getBytes());
		if (!passwordEncoder.matches(password, dbPassword)) throw new UsernameIsExitedException("密码错误");

		/*if (!dbPassword.equals(encoderPassword)) {
			throw new UsernameIsExitedException("密码错误");
		}*/

		// 还可以从数据库中查出该用户所拥有的权限,设置到 authorities 中去,这里模拟数据库查询.
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		//authorities.add(new GrantedAuthorityImpl("ADMIN"));

		Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);

		return auth;

	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
