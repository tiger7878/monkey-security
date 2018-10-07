package com.monkey.security.browser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 配置类
 * @author: monkey
 * @date: 2018/10/7 16:01
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    //密码加解密用它
    @Bean
    public PasswordEncoder passwordEncoder(){
        //它会随机生成盐+明文进行加解密，相同的明文每次生成的密文都不同，推荐用它，思路不错
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//form表单认证-页面
//          http.httpBasic()//basic认证-弹框
                .and()
                .authorizeRequests().anyRequest().authenticated();//所有请求都需要认证
    }
}
