package com.lbw.validateCode;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.lbw.common.MyAuthenticationException;

import java.util.Map;

/**
 * Created by lenovo on 2018-03-07.
 */
public abstract  class AbStractValidateCodeProcess<C extends ValidateCode> implements ValidateCodeProcess {

    /**
     * 获取所有ValidateCodeGenerator接口的实现
     */
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGeneratorMap;

    @Autowired
    @Qualifier("jdbcValidateCodeRepository")
    private ValidateCodeRepository validateCodeRepository;

	@Override
    public void create(ServletWebRequest request) throws Exception{
       ValidateType validateType =getValidateType(request);
        //生成验证码
        C validateCode = generator(request,validateType);
        //保存到session
        save(request,validateCode,validateType);
        //发送
        send(request,validateCode);
    }

    /**
     * 根据请求获取构建类型
     * @param request
     * @return
     */
    private  ValidateType getValidateType(ServletWebRequest request){
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcess");
        return ValidateType.valueOf(type.toUpperCase());
    }


    /**
     * 生成验证码
     * @param request
     * @param validateType
     * @return
     */
    private C generator(ServletWebRequest request,ValidateType validateType){
              String type =  validateType.toString().toLowerCase();
              String generatorName =  type+ValidateCodeGenerator.class.getSimpleName();
              ValidateCodeGenerator validateCodeGenerator=  validateCodeGeneratorMap.get(generatorName);
            if(validateCodeGenerator == null){
                throw new MyAuthenticationException("验证码生成器" + generatorName + "不存在");
            }
        return (C)validateCodeGenerator.getGenerator(request);
    }

    /**
     * 保存验证码
     * @param request
     * @param validateCode
     * @param validateType
     */
    private void save(ServletWebRequest request,C validateCode,ValidateType validateType){

      //  sessionStrategy.setAttribute(request,getSessionKey(request,validateType),validateCode);
        ValidateCode code = new ValidateCode(validateCode.getValiCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateType(request));

    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request,ValidateType validateType) {
        return SESSION_KEY_PREFIX + validateType.toString().toUpperCase();
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;


    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request,ValidateType validateType) {
//        ValidateCodeType processorType = getValidateCodeType(request);
        //String sessionKey = getSessionKey(request,validateType);
        C codeInSession = (C) validateCodeRepository.get(request, validateType);
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    validateType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {

            throw new MyAuthenticationException("获取验证码的值失败");
        }
        if (StringUtils.isBlank(codeInRequest)) {
            throw new MyAuthenticationException(validateType + "验证码的值不能为空");
        }
        if (codeInSession == null) {
            throw new MyAuthenticationException(validateType + "验证码不存在");
        }

//        if (codeInSession.isExpried()) {
//            validateCodeRepository.remove(request, validateType);
//            throw new ValidateCodeException(validateType + "验证码已过期");
//        }
        if (!StringUtils.equals(codeInSession.getValiCode().toUpperCase(), codeInRequest.toUpperCase())) {
            throw new MyAuthenticationException(validateType + "验证码不匹配");
        }
        validateCodeRepository.remove(request, validateType);

    }
}
