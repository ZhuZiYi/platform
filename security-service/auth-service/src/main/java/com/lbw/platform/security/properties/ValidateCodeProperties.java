package com.lbw.platform.security.properties;

/**
 * Created by lenovo on 2018-03-06.
 * 生成器合集属性
 */
public class ValidateCodeProperties {

    private ImagerCodeProperties imageCode = new ImagerCodeProperties();

    private SmsCodeProperties sms =new SmsCodeProperties();


    public SmsCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }

    public ImagerCodeProperties getImageCode() {
        return imageCode;
    }

    public void setImageCode(ImagerCodeProperties imageCode) {
        this.imageCode = imageCode;
    }
}
