package com.monkey.security.core.validate.code.sms;

/**
 * 短信验证码发送的默认实现
 *
 * @author: monkey
 * @date: 2018/10/17 22:08
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机号：" + mobile + " 发送了短信验证码：" + code);
    }

}
