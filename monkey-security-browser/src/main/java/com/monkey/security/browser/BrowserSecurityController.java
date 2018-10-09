package com.monkey.security.browser;

import com.monkey.security.browser.support.SimpleResponse;
import com.monkey.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 安全控制器
 * @author: monkey
 * @date: 2018/10/9 21:40
 */
@RestController
public class BrowserSecurityController {

    private Logger logger= LoggerFactory.getLogger(getClass());

    //这里面有请求的url地址
    private RequestCache requestCache=new HttpSessionRequestCache();

    //跳转工具
    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requiredAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //能否进入这里是由spring security来判断的，我们在config中配置好就行
        //之前缓存的请求
        SavedRequest savedRequest=requestCache.getRequest(request,response);

        if (savedRequest!=null){
            String targetUrl=savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是:"+targetUrl);
            //请求的url是.html结尾就引发跳转
            if (StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                //跳转
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }

        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }

}
