package com.monkey.security.core.validate.code.sms;

/**
 * 短信验证码发送接口
 * @author: monkey
 * @date: 2018/10/17 22:06
 */
public interface SmsCodeSender {

    void send(String mobile,String code);

}
