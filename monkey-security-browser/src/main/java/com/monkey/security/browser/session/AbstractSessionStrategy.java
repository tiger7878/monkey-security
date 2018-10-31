package com.monkey.security.browser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkey.security.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session处理策略
 *
 * @author: monkey
 * @date: 2018/10/28 11:31
 */
public class AbstractSessionStrategy {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 跳转的url
     */
    private String destinationUrl;

    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 跳转前是否创建新的session
     */
    private boolean createNewSession = true;

    private ObjectMapper objectMapper = new ObjectMapper();

    public AbstractSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    /**
     * session失效后的处理
     *
     * @param request
     * @param response
     */
    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (createNewSession) {
            request.getSession();
        }

        String sourceUrl = request.getRequestURI();//获取请求的url
        String targetUrl;//跳转的url地址

        // 如果请求的html页面，那么就跳转到指定的失效的html页面
        if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
            targetUrl = destinationUrl;
            logger.info("跳转到：" + targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            //否则就响应json数据回去
            Object result = buildResponse(request);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(result));
        }
    }

    /**
     * @param request
     * @return
     */
    private Object buildResponse(HttpServletRequest request) {
        String message = "session已失效";
        if (isConcurrency()) {
            message = message + "，有可能是并发登录导致的";
        }
        return new SimpleResponse(message);
    }

    /**
     * session失效是否是并发导致的
     *
     * @return
     */
    protected boolean isConcurrency() {
        return false;
    }

    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
