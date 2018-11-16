package com.lbw.platform.security.validateCode.sms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.lbw.platform.security.validateCode.AbStractValidateCodeProcess;
import com.lbw.platform.security.validateCode.ValidateCode;

/**
 * Created by lenovo on 2018-03-09.
 */

@Component("smsValidateCodeProcess")
public class SmsCodeProcess  extends AbStractValidateCodeProcess<ValidateCode> {

    /**
     * 短信验证码发送器
     */
    @Autowired
    private SmsCodeSend smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = "mobile";
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getValiCode());
    }
}