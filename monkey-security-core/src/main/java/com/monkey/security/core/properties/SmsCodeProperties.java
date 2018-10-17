package com.monkey.security.core.properties;

/**
 * 短信验证码配置信息类
 * @author: monkey
 * @date: 2018/10/17 22:24
 */
public class SmsCodeProperties {

    /**
     * 字符个数
     */
    private int length = 6;


    /**
     * 失效时间
     */
    private int expireIn = 60;

    /**
     * 需要拦截的url，多个用逗号隔开
     */
    private String url;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
