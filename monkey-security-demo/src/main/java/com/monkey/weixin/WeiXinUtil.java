package com.monkey.weixin;

import com.alibaba.fastjson.JSON;
import com.monkey.weixin.AccessTokenModel;
import com.monkey.weixin.SnsapiUserInfoModel;
import com.monkey.exception.RRException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 微信工具类
 * User: monkey
 * Date: 2020/1/20 14:41
 */
@ConfigurationProperties(prefix = "monkey.wx")
@Component
@Data
@Slf4j
public class WeiXinUtil {

    /**
     * 获取code的url
     */
    private static String GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    /**
     * 获取网页授权access_token的url
     */
    private static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    /**
     * 获取网页授权userinfo的url
     */
    private static String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 微信公众平台的appid
     */
    private String appid;

    /**
     * 微信公众平台的appsecret
     */
    private String appsecret;

    /**
     * 获取code的scope
     */
    private String scope;

    /**
     * 本网站接口地址
     */
    private String webApiDomain;


    /**
     * 获取code的url
     *
     * @param redirectUrl 回调地址
     * @return
     */
    public String getCodeUrl(String redirectUrl) {
        return GET_CODE_URL.replace("APPID", getAppid())
                .replace("REDIRECT_URI", redirectUrl)
                .replace("SCOPE", getScope());
    }

    /**
     * 获取网页授权access_token的url
     *
     * @param code
     * @return
     */
    private String getAccessTokenUrl(String code) {
        return GET_ACCESS_TOKEN_URL.replace("APPID", getAppid())
                .replace("SECRET", getAppsecret())
                .replace("CODE", code);
    }

    /**
     * 获取网页授权userinfo的url
     *
     * @param access_token
     * @param openid
     * @return
     */
    private String getUserInfoUrl(String access_token, String openid) {
        return GET_USER_INFO_URL.replace("ACCESS_TOKEN", access_token)
                .replace("OPENID", openid);
    }

    /**
     * 获取微信用户信息
     *
     * @param code 授权码
     * @return
     * @throws Exception
     */
    public SnsapiUserInfoModel getSnsapiUserInfoModel(String code) throws Exception {
        log.info("2、getSnsapiUserInfoModel code : {}", code);

        String url = getAccessTokenUrl(code);

        log.info("2、getSnsapiUserInfoModel getAccessTokenUrl : {}", url);

        //通过code换取网页授权access_token
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        AccessTokenModel accessTokenModel = JSON.parseObject(exchange.getBody(), AccessTokenModel.class);
        if (accessTokenModel == null) {
            throw new RRException("授权失败，请重新操作");
        }

        log.info("2、getSnsapiUserInfoModel AccessTokenModel : {}", accessTokenModel);

        //拉取用户信息(需scope为 snsapi_userinfo)
        url = getUserInfoUrl(accessTokenModel.getAccess_token(), accessTokenModel.getOpenid());

        log.info("2、getSnsapiUserInfoModel getUserInfoUrl : {}", url);

        exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        //中文乱码问题处理
        String json = new String(exchange.getBody().getBytes("ISO-8859-1"), "UTF-8");

        log.info("2、getSnsapiUserInfoModel userInfo json : {}", json);

        SnsapiUserInfoModel snsapiUserInfoModel = JSON.parseObject(json, SnsapiUserInfoModel.class);

        log.info("2、getSnsapiUserInfoModel snsapiUserInfoModel : {}", snsapiUserInfoModel);

        if (snsapiUserInfoModel == null) {
            throw new RRException("授权失败，请稍后操作");
        }

        return snsapiUserInfoModel;
    }
}
