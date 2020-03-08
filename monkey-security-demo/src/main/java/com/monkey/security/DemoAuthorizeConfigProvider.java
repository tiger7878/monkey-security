package com.monkey.security;

import com.monkey.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 这个是具体的demo的配置
 * @author: monkey
 * @date: 2018/11/10 15:15
 */
@Component
@Order(Integer.MAX_VALUE)
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //访问 /user/* 需要ADMIN权限
//        config.antMatchers("/user/*").hasRole("ADMIN");

        //验证数据库里面的动态权限
//        config.anyRequest().access("@rbacServiceImpl.hasPermission(request,authentication)");

        //对swagger的放行
        config.antMatchers("/swagger/**", "/swagger-ui.html", "/webjars/**").permitAll();
    }
}
