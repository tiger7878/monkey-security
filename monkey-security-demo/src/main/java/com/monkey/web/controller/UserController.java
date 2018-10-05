package com.monkey.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.monkey.dto.User;
import com.monkey.dto.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 用Pageable作为参数，方便传入分页相关的参数
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/user3",method = RequestMethod.GET)
    public List<User> query3(@PageableDefault(page = 1,size = 15,sort = "username",direction = Sort.Direction.DESC) Pageable pageable){
        //打印请求参数
        System.out.println("getPageNumber:"+pageable.getPageNumber());
        System.out.println("getPageSize:"+pageable.getPageSize());
        System.out.println("getSort:"+pageable.getSort());

        List<User> users=new ArrayList<User>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public User getInfo(@PathVariable String id){
        User user=new User();
        user.setUsername("monkey");
        return user;
    }

    /**
     * 根据id获取用户信息
     * 请求参数用正则来限定只能是数字
     * @param id
     * @return
     */
    @RequestMapping(value = "/user2/{id:\\d+}",method = RequestMethod.GET)
    public User getInfo2(@PathVariable String id){
        User user=new User();
        user.setUsername("monkey");
        return user;
    }

    /**
     * 使用JsonView来指定要显示的字段-不显示密码
     * @return
     */
    @RequestMapping(value = "/users4",method = RequestMethod.GET)
    @JsonView(User.UserSimpleView.class)
    public List<User> query4(){
        List<User> users=new ArrayList<User>();
        users.add(new User("monkey","123"));
        users.add(new User("tom","456"));
        users.add(new User("jack","789"));
        return users;
    }

    /**
     * 使用JsonView来指定要显示的字段-显示密码
     * @param id
     * @return
     */
    @RequestMapping(value = "/user3/{id}",method = RequestMethod.GET)
    @JsonView(User.UserDetailView.class)
    public User getInfo3(@PathVariable String id){
        User user=new User();
        user.setUsername("monkey");
        user.setPassword("123");
        return user;
    }
}
