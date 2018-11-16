package com.lbw.platform.security.validateCode.sms;

import com.aliyuncs.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lenovo on 2018-03-14.
 */
public class DefaultSmsCodeSend implements SmsCodeSend{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String templatteCode ;


    public String getTemplatteCode() {
        return templatteCode;
    }

    public void setTemplatteCode(String templatteCode) {
        this.templatteCode = templatteCode;
    }

    @Override
    public void send(String mobile, String code) {
//        logger.warn("请配置真实的短信验证码发送器（SmsCodeSender）");
        logger.info("向手机"+mobile+"发送验证码" + code);
        try {
            SmsDemo.sendSms(mobile,"好学家",templatteCode,code);
        }catch (ClientException e){
            logger.error("【系统异常】{}",e);
        }

    }
}
