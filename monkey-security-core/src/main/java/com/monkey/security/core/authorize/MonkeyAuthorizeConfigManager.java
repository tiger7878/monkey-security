package com.monkey.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author: monkey
 * @date: 2018/11/10 15:00
 */
@Component
public class MonkeyAuthorizeConfigManager implements AuthorizeConfigManager {

    //依赖查找，收集到所有的AuthorizeConfigProvider，都添加到配置项中
    @Autowired
    private Set<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.config(config);
        }
        //其他的都得认证
        config.anyRequest().authenticated();
    }

}
