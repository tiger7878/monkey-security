package com.monkey.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成接口
 * @author: monkey
 * @date: 2018/10/14 16:17
 */
public interface ValidateCodeGenerator {

    ImageCode generate(ServletWebRequest request);

}
