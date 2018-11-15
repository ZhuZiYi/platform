package com.lbw.validateCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lbw.properties.SecurityProperties;
import com.lbw.validateCode.image.ValidateImageCodeGenerator;
import com.lbw.validateCode.sms.DefaultSmsCodeSend;
import com.lbw.validateCode.sms.SmsCodeSend;
import com.lbw.validateCode.sms.ValidateSmsCodeGenerator;



@Configuration
public class ValidateCodeConfig {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 短信验证生成器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        ValidateImageCodeGenerator validateImageCodeGenerator = new ValidateImageCodeGenerator();
        validateImageCodeGenerator.setSecurityProperties(securityProperties);
        return validateImageCodeGenerator;
    }


    /**
     * 短信验证构建器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
    public  ValidateCodeGenerator smsValidateCodeGenerator(){
        ValidateSmsCodeGenerator validateSmsCodeGenerator = new ValidateSmsCodeGenerator();
        validateSmsCodeGenerator.setSecurityProperties(securityProperties);
        return  validateSmsCodeGenerator;
    }

    /**
     * 短信验证码发送器
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSend.class)
    public  SmsCodeSend smsCodeSend(){
        DefaultSmsCodeSend defaultSmsCodeSend =  new DefaultSmsCodeSend();
        defaultSmsCodeSend.setTemplatteCode(securityProperties.getValiCode().getSms().getTemplatteCode());
        return  defaultSmsCodeSend;
    }
}
