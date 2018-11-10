package com.monkey.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 配置项管理器，把所有配置项集中起来
 * @author: monkey
 * @date: 2018/11/10 14:59
 */
public interface AuthorizeConfigManager {
    /**
     * 安全配置
     * @param config HttpSecurity.authorizeRequests()方法的返回值
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
