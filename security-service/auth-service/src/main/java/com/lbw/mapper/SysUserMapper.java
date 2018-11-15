package com.lbw.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lbw.domain.SysUser;

@Mapper
public interface SysUserMapper {
	SysUser getSysUserByUserName(String username);
}
