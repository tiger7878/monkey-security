package com.monkey.security.core.authentication.openid;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

/**
 * 微信认证的校验逻辑
 * User: monkey
 * Date: 2020/6/8 15:13
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    //认证校验逻辑
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(OpenIdAuthenticationToken.class,authentication,"Only OpenIdAuthenticationToken is supported");

        UserDetails user=userDetailsService.loadUserByUsername((String) authentication.getPrincipal());

        if (user==null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        //认证后的信息（注意：认证前Principal存放的是手机号码，认证后存放的是UserDetails）
        OpenIdAuthenticationToken authenticationToken=new OpenIdAuthenticationToken(user,user.getAuthorities());
        //未认证时request相关信息已经存放到未认证的authentication中了，这里需要拷贝一份
        authenticationToken.setDetails(authentication.getDetails());

        return authenticationToken;
    }

    //用来判断是否用该类来处理校验逻辑
    @Override
    public boolean supports(Class<?> aClass) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
