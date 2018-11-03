package com.monkey.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 对验证码存储、获取、删除的接口
 * @author: monkey
 * @date: 2018/11/3 14:48
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     * @param request
     * @param validateCode
     * @param validateCodeType
     */
    void save(ServletWebRequest request,ValidateCode validateCode,ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request,ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     * @param request
     * @param validateCodeType
     */
    void remove(ServletWebRequest request,ValidateCodeType validateCodeType);

}
