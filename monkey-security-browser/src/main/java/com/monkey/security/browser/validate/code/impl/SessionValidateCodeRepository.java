package com.monkey.security.browser.validate.code.impl;

import com.monkey.security.core.validate.code.ValidateCode;
import com.monkey.security.core.validate.code.ValidateCodeRepository;
import com.monkey.security.core.validate.code.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author: monkey
 * @date: 2018/11/3 15:08
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType) {

        sessionStrategy.setAttribute(request, getSessionKey(request,validateCodeType),validateCode);

    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request,validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(request,validateCodeType));
    }

    /**
     * 获取保存到session中的key
     * @param request
     * @param validateCodeType
     * @return
     */
    private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFFIX + validateCodeType.toString().toUpperCase();
    }
}
