package com.monkey.security.core.properties;

/**
 * 图片验证码配置文件
 *
 * @author: monkey
 * @date: 2018/10/14 12:05
 */
public class ImageCodeProperties extends SmsCodeProperties {

    /**
     * 宽度
     */
    private int width = 67;

    /**
     * 高度
     */
    private int height = 23;

    public ImageCodeProperties() {
        setLength(4);//图片验证码默认长度为4
    }

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

}
