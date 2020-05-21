package com.monkey.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

/**
 * 短信认证的校验逻辑
 * @author: monkey
 * @date: 2018/10/20 12:16
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    //认证校验逻辑
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(SmsCodeAuthenticationToken.class,authentication,"Only SmsCodeAuthenticationToken is supported");

        UserDetails user=userDetailsService.loadUserByUsername((String) authentication.getPrincipal());

        if (user==null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        //认证后的信息（注意：认证前Principal存放的是手机号码，认证后存放的是UserDetails）
        SmsCodeAuthenticationToken authenticationToken=new SmsCodeAuthenticationToken(user,user.getAuthorities());
        //未认证时request相关信息已经存放到未认证的authentication中了，这里需要拷贝一份
        authenticationToken.setDetails(authentication.getDetails());

        return authenticationToken;
    }

    //用来判断是否用该类来处理校验逻辑
    @Override
    public boolean supports(Class<?> aClass) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
