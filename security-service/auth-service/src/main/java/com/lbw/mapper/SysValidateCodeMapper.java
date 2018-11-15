package com.lbw.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lbw.domain.SysValidateCode;

@Mapper
public interface SysValidateCodeMapper {
	void save(SysValidateCode sysValdCode);
	SysValidateCode getValidCodeByKey(String key);
	void deleteValidCodeByKey(String key);
}
