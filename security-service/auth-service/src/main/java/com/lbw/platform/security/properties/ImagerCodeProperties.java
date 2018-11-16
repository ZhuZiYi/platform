package com.lbw.platform.security.properties;

/**
 * Created by lenovo on 2018-03-06.
 */
public class ImagerCodeProperties extends  SmsCodeProperties{
    //    private String randString = "0123456789";//随机产生只有数字的字符串 private String
    //private String randString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生只有字母的字符串
//    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生数字与字母组合的字符串
    private String randString = "0123456789";//随机产生数字与字母组合的字符串
    private int width = 95;// 图片宽
    private int height = 25;// 图片高
    private int lineSize = 40;// 干扰线数量


    public ImagerCodeProperties(){
        setLength(4);
    }

    public String getRandString() {
        return randString;
    }

    public void setRandString(String randString) {
        this.randString = randString;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

}
