package com.monkey.web.controller;

import com.monkey.dto.User;
import com.monkey.dto.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: monkey
 * @date: 2018/10/5 17:14
 */
@RestController
public class UserController {

    /**
     * 请求参数写到方法中
     * @param username
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public List<User> query(@RequestParam(name = "username",required = false,defaultValue = "tom") String username){
        //打印请求参数
        System.out.println("username = "+username);

        List<User> users=new ArrayList<User>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }

    /**
     * 请求参数封装到实体中
     * @param userQueryCondition
     * @return
     */
    @RequestMapping(value = "/user2",method = RequestMethod.GET)
    public List<User> query2(UserQueryCondition userQueryCondition){
        //打印请求参数
        System.out.println(ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.MULTI_LINE_STYLE));

        List<User> users=new ArrayList<User>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }

}
