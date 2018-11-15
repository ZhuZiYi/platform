package com.lbw.validateCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
@RequestMapping("/code")
public class CodeController {
	 /**
     * 获取ValidateCodeProcess接口实现类
     */
    @Autowired
    private ValidateCodeProcessHalder validateCodeProcessHalder;
    
    @GetMapping("/{type}")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        ValidateCodeProcess validateCodeProcess = validateCodeProcessHalder.getValidateCodeProcess(type);
        validateCodeProcess.create(new ServletWebRequest(request,response));
    }
}
