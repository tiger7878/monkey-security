package com.monkey.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkey.security.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出成功后的控制器
 * @author: monkey
 * @date: 2018/10/29 22:04
 */
public class MonkeyLogoutSuccessHander implements LogoutSuccessHandler {

    private String signOutUrl;//退出的url地址

    public MonkeyLogoutSuccessHander(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    private Logger logger= LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        logger.info("退出登录");

        if (StringUtils.isBlank(signOutUrl)){
            //如果没有配置，那么就响应json回去
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        }else {
            //如果配置了，就做页面跳转到指定页面
            response.sendRedirect(signOutUrl);
        }
    }
}
