package com.monkey.security.core.properties;

/**
 * 第三方应用相关的配置
 * @author: monkey
 * @date: 2018/11/3 22:24
 */
public class OAuth2ClientProperties {

    /**
     * 应用的id
     */
    private String clientId;

    /**
     * 应用的密钥
     */
    private String clientSecret;

    /**
     * token有效期，如果不配置默认是0，代表永不过期
     */
    private int accessTokenValiditySeconds;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }
}
