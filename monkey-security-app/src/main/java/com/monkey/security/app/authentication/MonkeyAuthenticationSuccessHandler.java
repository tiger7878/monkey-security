package com.monkey.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkey.security.core.properties.LoginResponseType;
import com.monkey.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功的处理
 * @author: monkey
 * @date: 2018/10/10 21:11
 */
@Component("monkeyAuthenticationSuccessHandler")
public class MonkeyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        logger.info("登录成功！");

        //判断登录方式是否是json，如果是json就返回json，如果是页面跳转就执行页面跳转
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));//把认证对象响应回去
        }else {
            //页面跳转，会跳转到原来请求的页面
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
