package com.monkey.security.app;

import com.monkey.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 配置token的保存
 *
 * @author: monkey
 * @date: 2018/11/3 22:56
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    //因为有两个tokenStore，所以用ConditionalOnProperty作为区分
    //配置文件中 monkey.security.oauth2.storeType=redis时才采用这个配置
    @Bean
    @ConditionalOnProperty(prefix = "monkey.security.oauth2", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore() {
        //把token保存到redis中
        return new RedisTokenStore(redisConnectionFactory);
    }

    // 配置文件中 monkey.security.oauth2.storeType=jwt时就采用这个配置，matchIfMissing表示如果没有配置，也采用它
    // 当用jwt后Controller直接过去UserDetails得到的是null，网上有解决方案：https://blog.csdn.net/qq_39288456/article/details/105739223
    @Configuration
    @ConditionalOnProperty(prefix = "monkey.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());//这里设置的签名很重要，泄露了以后就会导致别人可以任意伪造后访问我们的系统
            return jwtAccessTokenConverter;
        }

        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer") //这么写的目的是给一个默认的实现，但是别人可以覆盖它，定义自己的，架构思想
        public TokenEnhancer jwtTokenEnhancer(){
            return new MonkeyJwtTokenEnhancer();
        }
    }

}
