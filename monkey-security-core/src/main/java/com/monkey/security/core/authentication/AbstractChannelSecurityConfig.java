package com.monkey.security.core.authentication;

import com.monkey.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 安全配置类
 * @author: monkey
 * @date: 2018/10/21 15:57
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler monkeyAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler monkeyAuthenticationFailureHandler;

    /**
     * 针对formLogin的特有配置
     * @param http
     */
    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(monkeyAuthenticationSuccessHandler)
                .failureHandler(monkeyAuthenticationFailureHandler);
    }

}
