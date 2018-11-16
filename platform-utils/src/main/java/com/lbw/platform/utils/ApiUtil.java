package com.lbw.platform.utils;

public final  class ApiUtil {

    private static  String COMMON_FAIL_CODE = "SERVICE_HANDLE_FAIL";
    private static final String  CODE_SUCCESS = "0";
    private static final String  CODE_ERRO = "1";

    /**
     * 获取处理成功ApiResult
     * @param t 返回结果
     * @param <T> 类型
     * @return
     */
    public static <T> ApiResult<T> getResult(T t){
        ApiResult<T> apiResult = new ApiResult<T>();
        apiResult.setResult(t);
        return apiResult;
    }

    /**
     * 获取处理成功
     * @param t 返回结果
     * @param message 成功信息
     * @return
     */
    public static <T> ApiResult<T> getSuccessResult(T t,String message){
        ApiResult<T> apiResult = new ApiResult<T>();
        apiResult.setResult(t);
        apiResult.setMsg(message);
        apiResult.setCode(CODE_SUCCESS);
        return apiResult;
    }

    /**
     * 获取处理成功
     * @param message 成功信息
     * @return
     */
    public static <T> ApiResult<T> getSuccessResult(String message){
        ApiResult<T> apiResult = new ApiResult<T>();
        apiResult.setMsg(message);
        apiResult.setCode(CODE_SUCCESS);
        return apiResult;
    }

    /**
     * 获取失败ApiResult
     * @param message 错误信息
     * @return
     */
    public static ApiResult getErrorResult(String message) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(CODE_ERRO);
        apiResult.setMsg(message);
        return apiResult;
    }


    /**
     * 获取失败ApiResult
     * @param message 错误信息
     * @return
     */
    public static <T> ApiResult<T> getErrorResult(T t,String message) {
        ApiResult<T> apiResult = new ApiResult<T>();
        apiResult.setResult(t);
        apiResult.setCode(CODE_ERRO);
        apiResult.setMsg(message);
        return apiResult;
    }

//    /**
//     * 获取处理失败的ApiResult
//     * @param message 错误信息
//     * @return
//     */
//    public static ApiResult getErrorResult(String message) {
//        ApiResult apiResult = new ApiResult();
//        apiResult.setCode(COMMON_FAIL_CODE);
//        apiResult.setMsg(message);
//        return apiResult;
//    }
}
