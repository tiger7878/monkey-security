package com.monkey.security.core;

import com.monkey.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: monkey
 * @date: 2018/10/9 22:15
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)//让SecurityProperties配置读取器生效
public class SecurityCoreConfig {

}
