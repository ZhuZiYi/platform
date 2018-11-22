package com.lbw.platform.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lbw.platform.admin.entity.SysAuthority;

public interface SysAuthorityMapper {
	List<SysAuthority> getSysAuthorityByUserName(String username);
	List<SysAuthority> getAllSysAuthority();
}
