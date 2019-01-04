package com.lbw.platform.security.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lbw.platform.security.domain.SysUser;
import com.lbw.platform.security.exception.UsernameIsExitedException;
import com.lbw.platform.security.mapper.SysUserMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private SysUserMapper userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser user = userRepository.getSysUserByUserName(username);
		if (user == null) {
			throw new UsernameIsExitedException("该用户不存在");
		}
		
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		return new User(user.getUsername(), user.getPassword(),authorities);
	}

}
