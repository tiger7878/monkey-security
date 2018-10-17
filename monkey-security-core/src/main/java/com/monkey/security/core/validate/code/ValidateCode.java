package com.monkey.security.core.validate.code;

import java.time.LocalDateTime;

/**
 * 验证码
 * @author: monkey
 * @date: 2018/10/17 21:43
 */
public class ValidateCode {

    //验证码内容
    private String code;

    //验证码过期时间
    private LocalDateTime expireTime;

    public ValidateCode(String code,int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);//多少秒后过期
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 判断验证码是否过期
     * @return
     */
    public boolean isExpried(){
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
