package com.lbw.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;


//@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
	
	private HashMap<String, Collection<ConfigAttribute>> map = null;

	/**
	 * 加载权限表中所有权限
	 */
	public void loadResourceDefine() {
		/*map = new HashMap<>();
		Collection<ConfigAttribute> array;
		ConfigAttribute cfg;

		array = new ArrayList();
		cfg = new SecurityConfig("query");
		array.add(cfg);

		map.put("/demo", array);*/
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (map == null)
			loadResourceDefine();
		// object 中包含用户请求的request 信息
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		AntPathRequestMatcher matcher;
		String resUrl;
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			resUrl = iter.next();
			matcher = new AntPathRequestMatcher(resUrl);
			if (matcher.matches(request)) {
				return map.get(resUrl);
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
