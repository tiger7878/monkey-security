package com.monkey.service.impl;

import com.monkey.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author: monkey
 * @date: 2018/10/6 11:19
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String greeting(String name) {
        return "hello "+name;
    }
}
