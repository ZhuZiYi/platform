package com.lbw.platform.security.validateCode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by lenovo on 2018-03-06.
 */
public interface ValidateCodeGenerator {

     ValidateCode getGenerator(ServletWebRequest request);
}
