package com.monkey.weixin;

import com.monkey.weixin.SnsapiUserInfoModel;
import com.monkey.weixin.WeiXinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微信授权控制器
 * @author: monkey
 * @date: 2020-6-8 22:10
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeiXinController {
    @Autowired
    private WeiXinUtil weiXinUtil;

    //跳转工具
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    //获取code的url
    @GetMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = "http://pdyfii.natappfree.cc/weixin/openId";
        String url = weiXinUtil.getCodeUrl(redirectUrl);
        //跳转
        redirectStrategy.sendRedirect(request, response, url);
    }

    //接收code获取access_token，再用access_token换openId
    @GetMapping("/openId")
    public void openId(HttpServletRequest request, HttpServletResponse response, String code) throws Exception {
        log.info("code : {}", code);

        SnsapiUserInfoModel snsapiUserInfoModel = weiXinUtil.getSnsapiUserInfoModel(code);
        log.info("snsapiUserInfoModel : {}", snsapiUserInfoModel);

        String url = "http://web.maibaopen.com/";
        //跳转
        redirectStrategy.sendRedirect(request, response, url);
    }


}
