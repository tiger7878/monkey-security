package com.monkey.security.browser;

import com.monkey.security.browser.authentication.MonkeyAuthenticationFailureHandler;
import com.monkey.security.browser.authentication.MonkeyAuthenticationSuccessHandler;
import com.monkey.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.monkey.security.core.properties.SecurityProperties;
import com.monkey.security.core.validate.code.SmsCodeFilter;
import com.monkey.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置类
 * @author: monkey
 * @date: 2018/10/7 16:01
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MonkeyAuthenticationSuccessHandler monkeyAuthenticationSuccessHandler;//登录成功的处理

    @Autowired
    private MonkeyAuthenticationFailureHandler monkeyAuthenticationFailureHandler;//登录失败的处理

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;//短信认证安全配置文件

    //密码加解密用它
    @Bean
    public PasswordEncoder passwordEncoder(){
        //它会随机生成盐+明文进行加解密，相同的明文每次生成的密文都不同，推荐用它，思路不错
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //验证码认证过滤器
        ValidateCodeFilter validateCodeFilter=new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(monkeyAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        //短信验认证过滤器
        SmsCodeFilter smsCodeFilter=new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(monkeyAuthenticationFailureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();

        http.addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class)//添加短信认证过滤器
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)//在UsernamePasswordAuthenticationFilter之前添加ValidateCodeFilter过滤器
                .formLogin()//form表单认证-页面
                .loginPage("/authentication/require")//自定义登录页面：在resources文件夹下
                .loginProcessingUrl("/authentication/form")//处理登录的url地址 spring security的
                .successHandler(monkeyAuthenticationSuccessHandler)//登录成功的处理
                .failureHandler(monkeyAuthenticationFailureHandler)//登录失败的处理
//          http.httpBasic()//basic认证-弹框
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        securityProperties.getBrowser().getLoginPage(),
                        "/code/*").permitAll()//登录页面、验证码接口不需要认证就可以访问
                .anyRequest().authenticated()//所有请求都需要认证
                .and()
                .csrf().disable()//先禁用csrf的防护，后面再开启
                .apply(smsCodeAuthenticationSecurityConfig);//添加短信认证安全配置类
    }
}
