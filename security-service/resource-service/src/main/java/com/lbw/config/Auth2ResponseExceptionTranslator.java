package com.lbw.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

public class Auth2ResponseExceptionTranslator implements WebResponseExceptionTranslator {
	private  final Logger logger = LoggerFactory.getLogger(Auth2ResponseExceptionTranslator.class);
	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		// TODO Auto-generated method stub
		logger.error("Auth2异常", e);
		
    	HttpHeaders responseHeaders = new HttpHeaders();
    	//responseHeaders.add("Content-type", "application/json;charset=utf-8");
    	
        Throwable throwable = e.getCause();
        if (throwable instanceof InvalidTokenException) {
           // log.info("token失效:{}", throwable);
            //return new ResponseEntity("{\"code\":-1,\"msg\":\"无效的TOKEN\"}",responseHeaders, HttpStatus.OK);
        	return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
       // return new ResponseEntity(new Message(e.getMessage(), String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value())), HttpStatus.METHOD_NOT_ALLOWED);
        //return new ResponseEntity("{\"code\":-2,\"msg\":\"方法不允许\"}",responseHeaders, HttpStatus.OK);
        return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
	}
}
