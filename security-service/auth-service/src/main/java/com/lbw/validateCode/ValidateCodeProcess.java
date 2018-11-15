package com.lbw.validateCode;


import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by lenovo on 2018-03-07.
 */
public interface ValidateCodeProcess {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建验证码
     */
    public void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     */
     public void  validate(ServletWebRequest request, ValidateType validateType);
}
