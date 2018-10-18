package com.monkey.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 * 生成的流程发送变化，不是创建、保存、发送，实现它就可以
 * 这个是在模板方法上再封装一个接口，更加灵活，比如说：网关和直连
 * @author: monkey
 * @date: 2018/10/18 22:05
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     * @param request 包含该了request和response
     * @throws Exception
     */
    void create(ServletWebRequest request)throws Exception;

}
