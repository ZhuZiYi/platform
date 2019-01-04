package com.lbw.platform.security.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lbw.platform.security.domain.SysUser;



@Mapper
public interface SysUserMapper {
	SysUser getSysUserByUserName(String username);
}
