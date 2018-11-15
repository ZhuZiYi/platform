package com.lbw.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by wangyunfei on 2017/6/12.
 */
@RestController
public class DemoController {
	@Autowired
	Producer producer;
    @GetMapping("/demo")
    //@PreAuthorize("hasAuthority('query-demo')")
    public String getDemo(Principal user){
    	
        return "good,"  + user.getName();
    }
    
    @GetMapping("/noauth/test")
    public String test(){
    	/*for (int i = 0;i<10;i++)
    		producer.sendMessage("qq", "test " + i);*/
    	//int a=0;
    	//int b = 100/a;
        return "test";
    }
}
