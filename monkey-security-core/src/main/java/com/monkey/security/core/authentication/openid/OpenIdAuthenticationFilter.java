package com.monkey.security.core.authentication.openid;

import com.monkey.security.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信认证的过滤器
 * 参考：UsernamePasswordAuthenticationFilter
 *
 * User: monkey
 * Date: 2020/6/8 15:03
 */
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String MONKEY_FORM_OPENID_KEY = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;
    private String openIdParameter = MONKEY_FORM_OPENID_KEY;
    private boolean postOnly = true;

    public OpenIdAuthenticationFilter() {
        //必须是post并且请求/authentication/openid这个才拦截
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID, "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String openId = this.obtainOpenId(request);
            if (openId == null) {
                openId = "";
            }

            openId = openId.trim();
            OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openId);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(this.openIdParameter);
    }

    protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setOpenIdParameter(String openIdParameter) {
        Assert.hasText(openIdParameter, "OpenId parameter must not be empty or null");
        this.openIdParameter = openIdParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getOpenIdParameter() {
        return this.openIdParameter;
    }

}
