package com.monkey.security.core.properties;

/**
 * 验证码配置文件类
 *
 * @author: monkey
 * @date: 2018/10/14 15:39
 */
public class ValidateCodeProperites {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms=new SmsCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }

    public SmsCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }
}
