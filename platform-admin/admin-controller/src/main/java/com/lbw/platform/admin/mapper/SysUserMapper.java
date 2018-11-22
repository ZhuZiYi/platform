package com.lbw.platform.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lbw.platform.admin.entity.SysUser;

public interface SysUserMapper {
	SysUser getSysUserByUserName(String username);
}
