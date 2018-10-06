package com.monkey.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义校验注解
 * @author: monkey
 * @date: 2018/10/6 11:20
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class) //指定校验类
public @interface MyConstraint {
    //以下这三个方法是必须的
    String message() default "自定义错误注解默认消息";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
