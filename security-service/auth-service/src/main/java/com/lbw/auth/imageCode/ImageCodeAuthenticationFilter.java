package com.lbw.auth.imageCode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lbw.validateCode.ValidateCodeProcess;
import com.lbw.validateCode.ValidateCodeProcessHalder;
import com.lbw.validateCode.ValidateType;

/*
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

	private Map<String,ValidateType> urlMap = new HashMap<>();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (StringUtils.equals("/oauth/token", request.getRequestURI())
				&& StringUtils.equals(request.getMethod().toLowerCase(), "post")) {
			System.out.println("拦截地址:" + request.getRequestURI());
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void afterPropertiesSet() throws ServletException {
		 super.afterPropertiesSet();
	     urlMap.put("/oauth/token",ValidateType.IMAGE);
	}
}*/

public class ImageCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {	
	private ValidateCodeProcessHalder validateCodeProcessHalder;
	
	public ValidateCodeProcessHalder getValidateCodeProcessHalder() {
		return validateCodeProcessHalder;
	}


	public void setValidateCodeProcessHalder(ValidateCodeProcessHalder validateCodeProcessHalder) {
		this.validateCodeProcessHalder = validateCodeProcessHalder;
	}

	private final String paramCode = "imageCode";
	private boolean postOnly = true;

	public ImageCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher("/admin/admin", "POST"));
	}


	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter.
	 * If set to true, and an authentication request is received which is not a
	 * POST request, an exception will be raised immediately and authentication
	 * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
	 * will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub

		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		if (request.getParameter(paramCode) == null) {
			throw new AuthenticationServiceException("请输入验证码!");
		}

		ValidateCodeProcess validateCodeProcess = validateCodeProcessHalder.getValidateCodeProcess("image");
		if (validateCodeProcess == null){
			throw new AuthenticationServiceException("获取校验器失败!");
		}
		
	
		//校验验证码是否正确
		validateCodeProcess.validate(new ServletWebRequest(request,response), ValidateType.IMAGE);

		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username == null || username.isEmpty()) throw new AuthenticationServiceException("缺少用户名!");
		if (password == null || password.isEmpty()) throw new AuthenticationServiceException("缺少密码!");
		
		  // 生成对应的AuthenticationToken
	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,password);
	    authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	 
	    return this.getAuthenticationManager().authenticate(authRequest);
	}

}
