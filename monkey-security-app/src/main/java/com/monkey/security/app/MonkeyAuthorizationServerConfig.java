package com.monkey.security.app;

import com.monkey.security.core.properties.OAuth2ClientProperties;
import com.monkey.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 认证服务器配置
 *
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

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    //对第三方应用的配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //在这里配置了应用以后，在配置文件中配置的就失效了
        //单个应用的配置方式
        /*clients.inMemory()
                .withClient("mkapp")
                .secret("mkappSecret")
                .accessTokenValiditySeconds(7200)//令牌的有效时间，单位秒
                .authorizedGrantTypes("refresh_token","password")//可以使用的授权码模式
                .scopes("all","read","write");//权限*/

        //多个应用的配置方式，从配置文件中读取
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        OAuth2ClientProperties[] clientProperties = securityProperties.getOauth2().getClients();
        if (ArrayUtils.isNotEmpty(clientProperties)) {
            for (OAuth2ClientProperties clientProperty : clientProperties) {
                builder.withClient(clientProperty.getClientId())
                        .secret(clientProperty.getClientSecret())
                        .accessTokenValiditySeconds(clientProperty.getAccessTokenValiditySeconds())
                        .authorizedGrantTypes("refresh_token", "password")
                        .scopes("all", "read", "write");
            }
        }
    }
}
