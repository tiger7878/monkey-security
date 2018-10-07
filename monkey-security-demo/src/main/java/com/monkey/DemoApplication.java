package com.monkey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: monkey
 * @date: 2018/10/5 15:30
 */
@SpringBootApplication
@RestController
@EnableSwagger2 //开启对swagger的支持
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "hello spring security";
    }

}
