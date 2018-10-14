package com.monkey.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 统一的配置文件管理类
 * @author: monkey
 * @date: 2018/10/9 22:09
 */
@ConfigurationProperties(prefix = "monkey.security")//读取以imooc.security开头的配置项
public class SecurityProperties {

    //以imooc.security.browser开头的配置项读取到这里
    private BrowserProperties browser=new BrowserProperties();

    //以imooc.security.code开头的验证码的配置项读取到这里
    private ValidateCodeProperites code=new ValidateCodeProperites();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperites getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperites code) {
        this.code = code;
    }
}
