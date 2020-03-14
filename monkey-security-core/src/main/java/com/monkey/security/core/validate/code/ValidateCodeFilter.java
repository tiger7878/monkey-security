package com.monkey.security.core.validate.code;

import com.monkey.security.core.properties.SecurityConstants;
import com.monkey.security.core.properties.SecurityProperties;
import com.monkey.security.core.validate.code.image.ImageCode;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 验证码过滤器
 * 每次请求过来先到这里
 * OncePerRequestFilter 保证只被执行一次
 * 实现InitializingBean可以在其他参数初始化以后初始化我们需要拦截的url
 * 过滤器负责拦截需要验证码校验的url地址，然后把校验交给验证码处理器来完成
 * @author: monkey
 * @date: 2018/10/13 15:51
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     * 用于模糊匹配比较 例如：/user/*
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 存放所有需要校验验证码的url和验证码类型
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
     * 其他配置属性加载完成以后，再执行这个
     * 把所有需要拦截的url和对应的验证码类型放到map中
     *
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }

    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urls
     * @param validateCodeType
     */
    private void addUrlToMap(String urls, ValidateCodeType validateCodeType) {

        if (StringUtils.isNotBlank(urls)) {
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urls, ",");
            for (String configUrl : configUrls) {
                urlMap.put(configUrl, validateCodeType);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ValidateCodeType validateCodeType = getValidateCodeType(request);

        if (validateCodeType!=null){
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + validateCodeType);

            try {
                //由对应的校验器进行校验
                validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType)
                        .validate(new ServletWebRequest(request,response));

                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request,response,exception);
                return;
            }

        }

        //不需要验证码的页面就直接放行
        filterChain.doFilter(request, response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {

        ValidateCodeType validateCodeType = null;

        //请求是post才处理，因为不管什么验证码，都是post请求
        if (!StringUtils.equalsIgnoreCase(request.getMethod(),"get")){
            Set<String> urls=urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url,request.getRequestURI())){
                    validateCodeType=urlMap.get(url);
                    break;
                }
            }
        }

        return validateCodeType;
    }
}
