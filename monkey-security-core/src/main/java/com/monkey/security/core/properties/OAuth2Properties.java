package com.monkey.security.core.properties;

/**
 * OAuth2相关配置
 *
 * @author: monkey
 * @date: 2018/11/3 22:25
 */
public class OAuth2Properties {

    private OAuth2ClientProperties[] clients = {};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }
}
