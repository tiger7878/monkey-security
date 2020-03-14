package com.monkey.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 * AuthenticationException是security身份证认证所有异常的基类
 * @author: monkey
 * @date: 2018/10/13 15:57
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
