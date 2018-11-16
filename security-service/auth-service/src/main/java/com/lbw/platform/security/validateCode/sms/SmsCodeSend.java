package com.lbw.platform.security.validateCode.sms;

/**
 * Created by lenovo on 2018-03-14.
 */

public interface SmsCodeSend {
    public  void send(String mobile, String code);
}
