package com.lbw.properties;

/**
 * Created by lenovo on 2018-03-07.
 */
public class SmsCodeProperties {

    private int length = 6;// 随机产生字符数量

    private  int expireIn =60;

    private String templatteCode ="SMS_129000073";

    private String org_join_code ="SMS_129000073";

    private  String url;


    public String getTemplatteCode() {
        return templatteCode;
    }

    public void setTemplatteCode(String templatteCode) {
        this.templatteCode = templatteCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getOrg_join_code() {
        return org_join_code;
    }

    public void setOrg_join_code(String org_join_code) {
        this.org_join_code = org_join_code;
    }
}
