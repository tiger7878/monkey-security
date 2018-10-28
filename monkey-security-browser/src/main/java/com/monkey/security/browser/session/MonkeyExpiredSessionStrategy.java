package com.monkey.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * session并发登录策略
 * 并发登录，单账号在不同浏览器上同时登录控制，当用户被T掉时触发该事件
 * @author: monkey
 * @date: 2018/10/27 17:55
 */
public class MonkeyExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public MonkeyExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        //重构前
        /*event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write("并发登录！");*/
        //重构后
        onSessionInvalid(event.getRequest(),event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
