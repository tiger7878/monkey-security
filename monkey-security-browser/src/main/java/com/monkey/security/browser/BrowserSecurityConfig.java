package com.monkey.security.browser;

import com.monkey.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SecurityProperties securityProperties;

    //密码加解密用它
    @Bean
    public PasswordEncoder passwordEncoder(){
        //它会随机生成盐+明文进行加解密，相同的明文每次生成的密文都不同，推荐用它，思路不错
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//form表单认证-页面
                .loginPage("/authentication/require")//自定义登录页面：在resources文件夹下
                .loginProcessingUrl("/authentication/form")//处理登录的url地址 spring security的
//          http.httpBasic()//basic认证-弹框
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        securityProperties.getBrowser().getLoginPage()).permitAll()//登录页面不需要认证就可以访问
                .anyRequest().authenticated()//所有请求都需要认证
                .and()
                .csrf().disable();//先禁用csrf的防护，后面再开启
    }
}
