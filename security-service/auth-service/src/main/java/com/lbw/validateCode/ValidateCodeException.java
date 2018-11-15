package com.lbw.validateCode;


import org.springframework.security.core.AuthenticationException;

/**
 * Created by lenovo on 2018-03-07.
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg){
        super(msg);
    }
}
