package com.lbw.platform.security.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lbw.platform.security.domain.SysAuthority;
import com.lbw.platform.security.domain.SysUser;

@Mapper
public interface SysAuthorityMapper {
	List<SysAuthority> getSysAuthorityByUserName(String username);
}
