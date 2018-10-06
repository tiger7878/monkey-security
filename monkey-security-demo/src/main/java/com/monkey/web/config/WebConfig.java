package com.monkey.web.config;

import com.monkey.web.filter.TimeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * web的配置类
 * @author: monkey
 * @date: 2018/10/6 15:44
 */
@Configuration
public class WebConfig {

    //第三方的filter注册
    //自定义的filter，不要标注@Component也可以在这里
    //在这里写的好处是可以控制哪些url经过该过滤器
    @Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();

        TimeFilter timeFilter=new TimeFilter();

        registrationBean.setFilter(timeFilter);

        List<String> urls=new ArrayList<>();
        urls.add("/*");//拦截所有url

        registrationBean.setUrlPatterns(urls);

        return registrationBean;
    }

}
