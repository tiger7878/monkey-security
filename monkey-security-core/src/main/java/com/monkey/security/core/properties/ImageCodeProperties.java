package com.monkey.security.core.properties;

/**
 * 图片验证码配置文件
 *
 * @author: monkey
 * @date: 2018/10/14 12:05
 */
public class ImageCodeProperties {

    /**
     * 宽度
     */
    private int width = 67;

    /**
     * 高度
     */
    private int height = 23;

    /**
     * 字符个数
     */
    private int length = 4;


    /**
     * 失效时间
     */
    private int expireIn = 60;

    /**
     * 需要拦截的url，多个用逗号隔开
     */
    private String url;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

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
