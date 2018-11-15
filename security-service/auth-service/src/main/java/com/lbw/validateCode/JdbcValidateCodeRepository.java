package com.lbw.validateCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.request.ServletWebRequest;

import com.lbw.common.MyAuthenticationException;
import com.lbw.domain.SysValidateCode;
import com.lbw.mapper.SysValidateCodeMapper;

@Component
@CrossOrigin
public class JdbcValidateCodeRepository implements ValidateCodeRepository {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysValidateCodeMapper sysValidateCodeMapper;

	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateType validateCodeType) {
		if (sysValidateCodeMapper.getValidCodeByKey(buildKey(request, validateCodeType)) != null)
			sysValidateCodeMapper.deleteValidCodeByKey(buildKey(request, validateCodeType));
		// TODO Auto-generated method stub
		sysValidateCodeMapper.save(new SysValidateCode(buildKey(request, validateCodeType), code.getValiCode(),code.getExpireTime().toEpochSecond(ZoneOffset.of("+8"))));
	}

	@Override
	public ValidateCode get(ServletWebRequest request, ValidateType validateCodeType) {
		// TODO Auto-generated method stub
		
		SysValidateCode sysValidateCode = sysValidateCodeMapper.getValidCodeByKey(buildKey(request, validateCodeType));
		if (sysValidateCode != null){
			return new ValidateCode(sysValidateCode.getvCode(),LocalDateTime.ofEpochSecond(sysValidateCode.getExpireTime(),0, ZoneOffset.ofHours(8)));
		}
		
		return null;
	}

	@Override
	public void remove(ServletWebRequest request, ValidateType codeType) {
		// TODO Auto-generated method stub

		sysValidateCodeMapper.deleteValidCodeByKey(buildKey(request, codeType));
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
