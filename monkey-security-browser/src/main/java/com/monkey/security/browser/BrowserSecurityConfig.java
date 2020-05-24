package com.monkey.security.browser;

import com.monkey.security.browser.authentication.MonkeyAuthenticationFailureHandler;
import com.monkey.security.browser.authentication.MonkeyAuthenticationSuccessHandler;
import com.monkey.security.browser.session.MonkeyExpiredSessionStrategy;
import com.monkey.security.core.authentication.AbstractChannelSecurityConfig;
import com.monkey.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.monkey.security.core.authorize.AuthorizeConfigManager;
import com.monkey.security.core.properties.SecurityConstants;
import com.monkey.security.core.properties.SecurityProperties;
import com.monkey.security.core.validate.code.SmsCodeFilter;
import com.monkey.security.core.validate.code.ValidateCodeFilter;
import com.monkey.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 浏览器项目安全配置类
 *
 * @author: monkey
 * @date: 2018/10/7 16:01
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MonkeyAuthenticationSuccessHandler monkeyAuthenticationSuccessHandler;//登录成功的处理

    @Autowired
    private MonkeyAuthenticationFailureHandler monkeyAuthenticationFailureHandler;//登录失败的处理

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;//短信认证安全配置文件

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;//session失效策略

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;//session并发过期策略

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;//退出成功后的控制器

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager; //认证的配置管理类

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //引入form表单认证相关信息
        applyPasswordAuthenticationConfig(http);

        //通过apply引入不同层级的配置文件
        http.apply(validateCodeSecurityConfig)
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .sessionManagement()
                    .invalidSessionStrategy(invalidSessionStrategy)
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions()) //同一个用户的最大session数量
                    .expiredSessionStrategy(sessionInformationExpiredStrategy) //并发控制触发事件
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())//加了该配置以后就是同一个账号后面的登录不能踢掉前面的登录，没有加之前是可以踢掉的
                    .and()
                    .and()
                .logout()
                    .logoutUrl("/signOut")//自定义退出的url地址，默认logout
                    .logoutSuccessHandler(logoutSuccessHandler)//退出成功后的处理器，控制跳转页面还是响应json
//                    .logoutSuccessUrl() //它和logoutSuccessHandler不能同时存在
                    .deleteCookies("JSESSIONID") //退出成功后要删除的cookie名，可以多条
                    .and()
                .csrf().disable();//先禁用csrf的防护，后面再开启

        //配置管理类来操作
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
