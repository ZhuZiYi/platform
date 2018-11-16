package com.lbw.platform.utils;

import java.util.Date;

public class ApiResult<T> {
    private T result;

    //    error_code 状态值：0 极为成功，其他数值代表失败
    private String code;

    //    error_msg 错误信息，若status为0时，为success
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "ApiResult{" +
                "result=" + result +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
