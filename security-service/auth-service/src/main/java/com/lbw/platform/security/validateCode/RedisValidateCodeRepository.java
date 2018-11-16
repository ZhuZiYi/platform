/**
 * 
 */
package com.lbw.platform.security.validateCode;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.request.ServletWebRequest;

import com.lbw.platform.security.common.MyAuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * 基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题
 * 
 * @author zhailiang
 *
 */
@Component
@CrossOrigin
public class RedisValidateCodeRepository implements ValidateCodeRepository {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeRepository#save(org.
	 * springframework.web.context.request.ServletWebRequest,
	 * com.imooc.security.core.validate.code.ValidateCode,
	 * com.imooc.security.core.validate.code.ValidateCodeType)
	 */
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateType type) {
		redisTemplate.opsForValue().set(buildKey(request, type), code, 60*24*7, TimeUnit.MINUTES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeRepository#get(org.
	 * springframework.web.context.request.ServletWebRequest,
	 * com.imooc.security.core.validate.code.ValidateCodeType)
	 */
	@Override
	public ValidateCode get(ServletWebRequest request, ValidateType type) {
		Object value = redisTemplate.opsForValue().get(buildKey(request, type));
		if (value == null) {
			return null;
		}
		return (ValidateCode) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeRepository#remove(org.
	 * springframework.web.context.request.ServletWebRequest,
	 * com.imooc.security.core.validate.code.ValidateCodeType)
	 */
	@Override
	public void remove(ServletWebRequest request, ValidateType type) {
		redisTemplate.delete(buildKey(request, type));
	}

	/**
	 * @param request
	 * @param type
	 * @return
	 */
	private String buildKey(ServletWebRequest request, ValidateType type) {
		HttpServletRequest request1=(HttpServletRequest) request.getNativeRequest();
		        Enumeration headerNames =
						request1.getHeaderNames();
        logger.info("获取请求头部！！！   Enumeration ==" + headerNames);
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            logger.info("请求头部！！！   headerNames  key ==" + key +"    values = " + value);
        }
		HttpServletRequest httpServletRequest = request.getRequest();
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId)) {
			deviceId = StringUtils.isBlank(request.getParameter("deviceId"))?httpServletRequest.getRequestedSessionId():request.getParameter("deviceId");
			    if(StringUtils.isBlank(deviceId)){
                    throw new MyAuthenticationException("请在请求头中携带deviceId参数");
                }
		}
        logger.info("deviceId" + deviceId);
		String key ="code:" + type.toString().toLowerCase() + ":" + deviceId;
		return key;
	}

}
