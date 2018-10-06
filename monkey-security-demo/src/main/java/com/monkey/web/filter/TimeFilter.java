package com.monkey.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * 自定义Filter，记得标注为组件
 * @author: monkey
 * @date: 2018/10/6 15:33
 */
//@Component //模拟第三方过滤器时先注释掉，自定义过滤器时记得恢复
public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Long startTime=new Date().getTime();

        filterChain.doFilter(servletRequest,servletResponse);//放行

        System.out.println("请求本方法耗时："+((new Date().getTime())-startTime)+" 毫秒！");

        System.out.println("time filter doFilter");
    }

    @Override
    public void destroy() {
        System.out.println("time filter destory");
    }

}
