package com.monkey.security.core.authentication.openid;

import com.monkey.security.core.authentication.mobile.SmsCodeAuthenticationFilter;
import com.monkey.security.core.authentication.mobile.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 微信登录认证安全配置
 * User: monkey
 * Date: 2020/6/8 15:19
 */
@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler monkeyAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler monkeyAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //过滤器
        OpenIdAuthenticationFilter openIdAuthenticationFilter=new OpenIdAuthenticationFilter();
        openIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(monkeyAuthenticationSuccessHandler);//成功的处理器
        openIdAuthenticationFilter.setAuthenticationFailureHandler(monkeyAuthenticationFailureHandler);//失败的处理器

        //认证处理器
        OpenIdAuthenticationProvider openIdAuthenticationProvider=new OpenIdAuthenticationProvider();
        openIdAuthenticationProvider.setUserDetailsService(userDetailsService);

        //向http中添加认证处理器和过滤器，加入到安全框架中
        http.authenticationProvider(openIdAuthenticationProvider)
                .addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
