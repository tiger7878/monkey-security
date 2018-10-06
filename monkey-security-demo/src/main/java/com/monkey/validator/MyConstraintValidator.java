package com.monkey.validator;

import com.monkey.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义错误校验
 * MyConstraint 是自定义错误注解
 * Object 是指定对什么类型的数据进行校验
 * @author: monkey
 * @date: 2018/10/6 11:21
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {

    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("MyConstraintValidator...initialize");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        //这里执行校验逻辑，返回true或者false
        System.out.println("helloService : "+helloService.greeting("tom"));
        System.out.println("需要校验的数据："+o);
        return false;
    }
}
