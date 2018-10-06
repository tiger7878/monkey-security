package com.monkey.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 自定义拦截器，可以获取请求的控制器，方法名等
 * @author: monkey
 * @date: 2018/10/6 16:06
 */
@Component //不要忘记这个注解
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //原方法执行前执行
        System.out.println("preHandle");

        System.out.println("控制器名字："+((HandlerMethod)handler).getBean().getClass().getName());
        System.out.println("方法名："+((HandlerMethod)handler).getMethod().getName());

        //把开始时间保存起来，方便计算整体耗时
        httpServletRequest.setAttribute("startTime",new Date().getTime());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //原方法执行后执行，如果抛出异常，本方法不执行
        System.out.println("postHandle");

        Long startTime= (Long) httpServletRequest.getAttribute("startTime");

        System.out.println("postHandle本次耗时："+((new Date().getTime())-startTime)+" 秒");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) throws Exception {
        //原方法执行后执行，不管是否有异常，本方法都执行
        //如果本项目有自定义异常处理类，那么自定义的异常在这里的ex将为空，因为它先被处理了，非自定义异常信息这里任然有
        // /user/2 和 /user/3就可以看出区别
        System.out.println("afterCompletion");

        Long startTime= (Long) httpServletRequest.getAttribute("startTime");

        System.out.println("afterCompletion本次耗时："+((new Date().getTime())-startTime)+" 秒");

        System.out.println("ex : "+ex);//打印异常信息
    }
}
