package com.lbw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lbw.domain.SysAuthority;
import com.lbw.domain.SysUser;

@Mapper
public interface SysAuthorityMapper {
	List<SysAuthority> getSysAuthorityByUserName(String username);
}
