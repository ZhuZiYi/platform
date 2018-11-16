package com.lbw.platform.security.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lbw.platform.security.domain.SysAuthority;
import com.lbw.platform.security.domain.SysUser;
import com.lbw.platform.security.mapper.SysAuthorityMapper;
import com.lbw.platform.security.mapper.SysUserMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by wangyunfei on 2017/6/9.
 */
// @Service("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

	@Autowired
	private SysUserMapper sysUserMapper;
	/*@Autowired
	private SysAuthorityMapper sysAuthorityMapper;*/

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String lowcaseUsername = username.toLowerCase();

		// 查询用户名
		SysUser realUser = sysUserMapper.getSysUserByUserName(lowcaseUsername);

		if (realUser == null)
			throw new UsernameNotFoundException("用户" + lowcaseUsername + "不存在!");
		
		
		
		Set<GrantedAuthority> setAuthority = new HashSet();				
		/*  List<SysAuthority> authority = sysAuthorityMapper.getSysAuthorityByUserName(lowcaseUsername);
		 for (SysAuthority au : authority){			
			setAuthority.add(new SimpleGrantedAuthority(au.getValue()));
		}*/
		

		return new User(realUser.getUsername(),realUser.getPassword(),setAuthority);
	}
}
