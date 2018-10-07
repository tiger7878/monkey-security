package com.monkey.security.browser;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 配置类
 * @author: monkey
 * @date: 2018/10/7 16:01
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//form表单认证-页面
//          http.httpBasic()//basic认证-弹框
                .and()
                .authorizeRequests().anyRequest().authenticated();//所有请求都需要认证
    }
}
