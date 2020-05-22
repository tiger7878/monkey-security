package com.monkey.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 统一的配置文件管理类
 * 说明：如果这个SecurityProperties不用这个名字，那么可以不用SecurityCoreConfig来让它生效，测试过
 * @author: monkey
 * @date: 2018/10/9 22:09
 */
@ConfigurationProperties(prefix = "monkey.security")//读取以imooc.security开头的配置项
public class SecurityProperties {

    //以imooc.security.browser开头的配置项读取到这里
    //这里new的好处是如果demo没有配置，也不会报错，因为有默认的实体
    private BrowserProperties browser = new BrowserProperties();

    //以imooc.security.code开头的验证码的配置项读取到这里
    private ValidateCodeProperties code = new ValidateCodeProperties();

    //oauth2相关配置
    private OAuth2Properties oauth2 = new OAuth2Properties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }
}
