package com.monkey.web.controller;

import com.monkey.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义控制器异常处理器，处理控制器抛出的异常
 * @author: monkey
 * @date: 2018/10/6 15:13
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)//指定处理哪一类异常
    @ResponseBody//响应json
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //指定响应码
    public Map<String,Object> handleUserNotExistException(UserNotExistException ex){
        Map<String,Object> map=new HashMap<>();
        map.put("id",ex.getId());
        map.put("message",ex.getMessage());
        return map;
    }

}
