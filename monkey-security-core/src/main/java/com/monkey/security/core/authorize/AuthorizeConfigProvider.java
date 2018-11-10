package com.monkey.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 配置抽取类
 * @author: monkey
 * @date: 2018/11/10 14:51
 */
public interface AuthorizeConfigProvider {

    /**
     * 安全配置
     * @param config HttpSecurity.authorizeRequests()方法的返回值
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
