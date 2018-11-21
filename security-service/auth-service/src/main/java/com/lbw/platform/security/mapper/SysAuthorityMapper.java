package com.lbw.platform.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lbw.platform.security.domain.SysAuthority;


@Mapper
public interface SysAuthorityMapper {
	List<SysAuthority> getSysAuthorityByUserName(String username);
}
