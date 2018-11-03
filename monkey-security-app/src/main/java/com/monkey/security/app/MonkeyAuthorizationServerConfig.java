package com.monkey.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * 认证服务器配置
 * @author: monkey
 * @date: 2018/10/31 20:47
 */
@Configuration
@EnableAuthorizationServer //认证服务器
public class MonkeyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    //对第三方应用的配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //在这里配置了应用以后，在配置文件中配置的就失效了
        clients.inMemory()
                .withClient("mkapp")
                .secret("mkappSecret")
                .accessTokenValiditySeconds(7200)//令牌的有效时间，单位秒
                .authorizedGrantTypes("refresh_token","password")//可以使用的授权码模式
                .scopes("all","read","write");//权限
    }
}
