package com.lbw.utils;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@ControllerAdvice
@Slf4j
public class ExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult exceptionGet(HttpServletRequest request,
                               HttpServletResponse response, Exception e) throws Exception {
       /* if(e instanceof RuntimeException){
            return ApiUtil.getErrorResult(e.getMessage());
        }else {
            throw new Exception(e.getMessage());
        }*/
    	
    	log.error(e.getMessage());
    	
    	 return ApiUtil.getErrorResult("服务器错误!");
    }
}
