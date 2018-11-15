package com.lbw.validateCode.sms;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import com.lbw.properties.SecurityProperties;
import com.lbw.validateCode.ValidateCode;
import com.lbw.validateCode.ValidateCodeGenerator;

/**
 * Created by lenovo on 2018-03-07.
 */
public class ValidateSmsCodeGenerator implements ValidateCodeGenerator {


    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode getGenerator(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getValiCode().getSms().getLength());

        return new ValidateCode(code,securityProperties.getValiCode().getSms().getExpireIn());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }


    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
