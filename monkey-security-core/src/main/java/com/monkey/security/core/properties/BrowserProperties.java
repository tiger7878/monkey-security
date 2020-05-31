package com.monkey.security.core.properties;

/**
 * browser相关的配置
 *
 * @author: monkey
 * @date: 2018/10/9 22:12
 */
public class BrowserProperties {

    private SessionProperties session = new SessionProperties();

    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;//默认的登录页面

    private LoginResponseType loginType = LoginResponseType.JSON;//默认json

    private String signOutUrl = "/demo-signOut.html";//这里需要提供一个默认的地址，而且要加入放行里面去

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

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }
}
