package com.lbw.platform.security.auth.smsCode;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.context.request.ServletWebRequest;

import com.lbw.platform.security.validateCode.ValidateCodeProcess;
import com.lbw.platform.security.validateCode.ValidateCodeProcessHalder;
import com.lbw.platform.security.validateCode.ValidateType;

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private ValidateCodeProcessHalder validateCodeProcessHalder;

	public ValidateCodeProcessHalder getValidateCodeProcessHalder() {
		return validateCodeProcessHalder;
	}

	public void setValidateCodeProcessHalder(ValidateCodeProcessHalder validateCodeProcessHalder) {
		this.validateCodeProcessHalder = validateCodeProcessHalder;
	}

	private final String mobileParam = "mobile";
	private final String paramCode = "smsCode";
	private boolean postOnly = true;

	public SmsCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher("/admin/mobile", "POST"));
	}

	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobileParameter = request.getParameter(mobileParam);
        if(mobileParameter == null){
            mobileParameter = "";
        }
        
        ValidateCodeProcess validateCodeProcess = validateCodeProcessHalder.getValidateCodeProcess("sms");
		if (validateCodeProcess == null){
			throw new AuthenticationServiceException("获取校验器失败!");
		}
		
      //校验验证码是否正确
       validateCodeProcess.validate(new ServletWebRequest(request,response), ValidateType.SMS);

        mobileParameter = mobileParameter.trim();
        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(mobileParameter);

  
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        return this.getAuthenticationManager().authenticate(authRequest);
	}

}
