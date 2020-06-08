package com.monkey.weixin;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信的用户信息实体
 * User: monkey
 * Date: 2020/1/20 17:03
 */
@Data
public class SnsapiUserInfoModel implements Serializable {
    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
}
