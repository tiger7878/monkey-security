package com.monkey.security.core.properties;

/**
 * browser相关的配置
 *
 * @author: monkey
 * @date: 2018/10/9 22:12
 */
public class BrowserProperties {

    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;//默认的登录页面

    private LoginResponseType loginType = LoginResponseType.JSON;//默认json

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginResponseType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }
}
