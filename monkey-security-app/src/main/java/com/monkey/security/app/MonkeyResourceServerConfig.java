package com.monkey.security.app;

import com.monkey.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.monkey.security.core.authorize.AuthorizeConfigManager;
import com.monkey.security.core.properties.SecurityConstants;
import com.monkey.security.core.properties.SecurityProperties;
import com.monkey.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 资源服务器配置类
 *
 * @author: monkey
 * @date: 2018/10/31 21:16
 */
@Configuration
@EnableResourceServer
public class MonkeyResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler monkeyAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler monkeyAuthenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;//短信认证安全配置文件

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(monkeyAuthenticationSuccessHandler)
                .failureHandler(monkeyAuthenticationFailureHandler);

                http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .csrf().disable();//先禁用csrf的防护，后面再开启

        //配置管理类来操作
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
