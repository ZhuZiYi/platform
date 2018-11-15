package com.lbw.validateCode;

/**
 * Created by lenovo on 2018-03-07.
 */
public enum  ValidateType {

        SMS{
            @Override
            public String getParamNameOnValidate() {
                return "smsCode";
            }
        },
        IMAGE{
            @Override
            public String getParamNameOnValidate() {
                return "imageCode";
            }
        };

    /**
     * 校验时从请求中获取的参数名
     */
    public abstract  String getParamNameOnValidate();


}
