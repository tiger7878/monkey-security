package com.monkey.security.core.properties;

/**
 * browser相关的配置
 * @author: monkey
 * @date: 2018/10/9 22:12
 */
public class BrowserProperties {

    private String loginPage="/monkey-signIn.html";//默认的登录页面

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
