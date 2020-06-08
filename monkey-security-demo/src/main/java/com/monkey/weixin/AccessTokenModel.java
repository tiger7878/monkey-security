package com.monkey.weixin;

import lombok.Data;

import java.io.Serializable;

/**
 * 通过code换取网页授权access_token的返回实体
 * User: monkey
 * Date: 2020/1/20 16:21
 */
@Data
public class AccessTokenModel implements Serializable {

    private String access_token;
    private long expires_in;
    private String refresh_token;
    private String openid;
    private String scope;

}
