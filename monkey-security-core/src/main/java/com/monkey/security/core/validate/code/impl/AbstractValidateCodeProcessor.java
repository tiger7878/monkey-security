package com.monkey.security.core.validate.code.impl;

import com.monkey.security.core.validate.code.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 模板方法：验证码的处理
 *
 * @author: monkey
 * @date: 2018/10/18 22:12
 */
@Slf4j
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 依赖查找：收集系统中所有的 ValidateCodeGenerator 接口的实现，key是名字
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 对验证码的保存、查询、删除
     */
    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        //生成
        C validateCode = generate(request);
        //保存
        save(request, validateCode);
        //发送
        send(request, validateCode);
    }

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        String type = getProcessType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存验证码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        //原来的图片验证码里面的图片对象是没有实现序列化接口，修改为保存到redis中会报错
        //本次做一个修改，保存验证码时只保存验证码的内容和过期时间
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        /*sessionStrategy.setAttribute(request,
                SESSION_KEY_PREFFIX + getProcessType(request).toUpperCase(),
                code);*/
        log.info("validate code : {}", validateCode.getCode());
        validateCodeRepository.save(request, code, getValidateCodeType(request));
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    private String getProcessType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }

    /**
     * 验证码的校验逻辑
     *
     * @param request
     */
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType codeType = getValidateCodeType(request);

        //获取保存验证码时存放到session中的key
        String sessionKey = getSessionKey(request);

        //根据key从session中获取到验证码对象
//        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);
        C codeInSession = (C) validateCodeRepository.get(request, codeType);

        //验证码的值
        String codeInRequest;

        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpired()) {
//            sessionStrategy.removeAttribute(request, sessionKey);
            validateCodeRepository.remove(request, codeType);
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        //验证码校验通过以后记得删除验证码，防止重复使用
//        sessionStrategy.removeAttribute(request, sessionKey);
        validateCodeRepository.remove(request, codeType);
    }

    /**
     * 获取构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {

        return SESSION_KEY_PREFFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        //获取当前实例的简单类名，比如SmsCodeProecessor，然后截取得到Sms
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");

        return ValidateCodeType.valueOf(type.toUpperCase());
    }
}
