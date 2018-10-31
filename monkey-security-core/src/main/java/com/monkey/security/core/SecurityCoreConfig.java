package com.monkey.security.core;

import com.monkey.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: monkey
 * @date: 2018/10/9 22:15
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)//让SecurityProperties配置读取器生效
public class SecurityCoreConfig {

    //密码加解密用它
    @Bean
    public PasswordEncoder passwordEncoder() {
        //它会随机生成盐+明文进行加解密，相同的明文每次生成的密文都不同，推荐用它，思路不错
        return new BCryptPasswordEncoder();
    }
}
