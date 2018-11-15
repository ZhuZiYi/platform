package com.lbw.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbw.properties.SecurityProperties;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 自定义成功处理 Created by lenovo on 2018-03-04.
 */
@Component("qhauthenticationSuccessHadler")
@CrossOrigin
public class QhauthenticationSuccessHadler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private SecurityProperties securitProperties;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Enumeration headerNames = request.getHeaderNames();
		logger.info("登录成功！！！   Enumeration ==" + headerNames);
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			logger.info("登录成功！！！   headerNames  key ==" + key + "    values = " + value);
		}
		
		String clienId = securitProperties.getClientId();
		String clienSecret = securitProperties.getClientSecurit();

		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clienId);
		if (clientDetails == null) {
			throw new UnapprovedClientAuthenticationException("clienId对应的配置信息不存在" + clienId);
		} else if (!StringUtils.equalsIgnoreCase(clientDetails.getClientSecret(), clienSecret)) {
			throw new UnapprovedClientAuthenticationException("clienSecret不匹配" + clienSecret);
		}

		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clienId, clientDetails.getScope(), "custom");
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setStatus(200);
		response.getWriter().write(objectMapper.writeValueAsString(token));
		
	}

	private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, "UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

}
