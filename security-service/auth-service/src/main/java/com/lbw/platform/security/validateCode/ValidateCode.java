package com.lbw.platform.security.validateCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by lenovo on 2018-03-07.
 */
public  class ValidateCode implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1588203828504660915L;

    private String valiCode;
    private LocalDateTime expireTime;

    public ValidateCode(){}

    public ValidateCode(String valiCode,LocalDateTime expireTime){
        this.valiCode=valiCode;
        this.expireTime =expireTime;
    }

    public ValidateCode(String valiCode,int expireIn){
        this.valiCode =valiCode;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }


    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
