package com.lbw.platform.admin.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.lbw.platform.admin.entity.SysAuthority;
import com.lbw.platform.admin.entity.SysUser;
import com.lbw.platform.admin.mapper.SysAuthorityMapper;
import com.lbw.platform.admin.mapper.SysUserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	private SysAuthorityMapper sysAuthorityMapper;
	
	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		log.info("用户的用户名: {}", username);

		/*String password = passwordEncoder.encode("123456");
		log.info("password: {}", password);*/

		
		/*String []authoritys = {"/demo.html","/page01.html","/page02.html"};
		Set<GrantedAuthority> setAuthority = new HashSet();	
		for (String s : authoritys){
			setAuthority.add(new SimpleGrantedAuthority(s));
		}*/
		
		SysUser sysUser = sysUserMapper.getSysUserByUserName(username);
		if (sysUser == null)
			throw new UsernameNotFoundException("用户" + username + "不存在!");
		
		Set<GrantedAuthority> setAuthority = new HashSet();	
		List<SysAuthority> lstAuthority = sysAuthorityMapper.getSysAuthorityByUserName(username);
		for (SysAuthority s : lstAuthority){
			setAuthority.add(new SimpleGrantedAuthority(s.getName()));
		}
		
		// 参数分别是：用户名，密码，用户权限
		User user = new User(username, sysUser.getPassword(), setAuthority);
		return user;
	}

}
