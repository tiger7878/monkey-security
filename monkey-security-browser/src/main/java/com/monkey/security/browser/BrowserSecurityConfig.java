package com.monkey.security.browser;

import com.monkey.security.browser.authentication.MonkeyAuthenticationFailureHandler;
import com.monkey.security.browser.authentication.MonkeyAuthenticationSuccessHandler;
import com.monkey.security.browser.session.MonkeyExpiredSessionStrategy;
import com.monkey.security.core.authentication.AbstractChannelSecurityConfig;
import com.monkey.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.monkey.security.core.properties.SecurityConstants;
import com.monkey.security.core.properties.SecurityProperties;
import com.monkey.security.core.validate.code.SmsCodeFilter;
import com.monkey.security.core.validate.code.ValidateCodeFilter;
import com.monkey.security.core.validate.code.ValidateCodeSecurityConfig;
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


    //密码加解密用它
    @Bean
    public PasswordEncoder passwordEncoder() {
        //它会随机生成盐+明文进行加解密，相同的明文每次生成的密文都不同，推荐用它，思路不错
        return new BCryptPasswordEncoder();
    }

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
                .invalidSessionUrl("/session/invalid") //session失效的后跳转的地址
                .maximumSessions(1) //同一个用户的最大session数量
                .expiredSessionStrategy(new MonkeyExpiredSessionStrategy()) //并发控制触发事件
                .and()
                .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        "/session/invalid")
                .permitAll()//登录页面、验证码接口不需要认证就可以访问
                .anyRequest()
                .authenticated()//所有请求都需要认证
                .and()
                .csrf().disable();//先禁用csrf的防护，后面再开启
    }
}
