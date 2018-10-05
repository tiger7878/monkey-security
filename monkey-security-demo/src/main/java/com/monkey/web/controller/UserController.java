package com.monkey.web.controller;

import com.monkey.dto.User;
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

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public List<User> query(@RequestParam(name = "username",required = false,defaultValue = "tom") String username){
        System.out.println("username = "+username);

        List<User> users=new ArrayList<User>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }

}
