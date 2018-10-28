package com.monkey.security.browser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session失效策略
 * @author: monkey
 * @date: 2018/10/28 13:07
 */
public class MonkeyInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

    public MonkeyInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        onSessionInvalid(request,response);
    }
}
