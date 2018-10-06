package com.monkey.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义切面
 * @author: monkey
 * @date: 2018/10/6 16:44
 */
@Aspect
@Component
public class TimeAspect {

    //环绕拦截UserController下所有方法
    @Around("execution(* com.monkey.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("time aspect start");

        //获取被拦截方法的参数
        Object[] args=pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg : "+arg);
        }

        //获取控制器的类
        String classType=pjp.getTarget().getClass().getName();
        System.out.println("classType : "+classType);

        //获取方法名
        String methodName=pjp.getSignature().getName();
        System.out.println("methodName : "+methodName);

        //用于计算耗时
        long start = new Date().getTime();

        Object object= pjp.proceed();//方法执行，异常被抛出，一旦出了异常，下面的方法就不执行了

        System.out.println("time aspect 耗时:"+ (new Date().getTime() - start));

        System.out.println("time aspect end");

        return object;
    }

}
