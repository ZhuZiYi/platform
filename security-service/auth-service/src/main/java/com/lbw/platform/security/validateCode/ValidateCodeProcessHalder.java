package com.lbw.platform.security.validateCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lbw.platform.security.common.MyAuthenticationException;

import java.util.Map;

/**
 * Created by lenovo on 2018-03-07.
 */
@Component("validateCodeProcessHalder")
public class ValidateCodeProcessHalder {

    /**
     * 获取所有ValidateCodeProcess接口的实现
     */
    @Autowired
    private Map<String,ValidateCodeProcess> validateCodeProcesss;


    public ValidateCodeProcess getValidateCodeProcess(ValidateType validateType){
          return   getValidateCodeProcess(validateType.toString().toLowerCase());
    }

    public ValidateCodeProcess getValidateCodeProcess(String validateType)  {
        String name =validateType.toLowerCase()+ValidateCodeProcess.class.getSimpleName();
        ValidateCodeProcess process= validateCodeProcesss.get(name);
        if(process == null){
                throw new MyAuthenticationException("验证码处理器" + name + "不存在");
        }
        return  process;
    }
}
