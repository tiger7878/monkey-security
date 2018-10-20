package com.monkey.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkey.security.browser.support.SimpleResponse;
import com.monkey.security.core.properties.LoginResponseType;
import com.monkey.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败处理
 * @author: monkey
 * @date: 2018/10/10 21:30
 */
@Component("monkeyAuthenticationFailureHandler")
public class MonkeyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");

        //判断是json方式还是页面跳转
        if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
        }else {
            //页面跳转，去到默认的错误页
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
