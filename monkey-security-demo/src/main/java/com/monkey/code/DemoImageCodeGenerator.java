package com.monkey.code;

import com.monkey.security.core.validate.code.image.ImageCode;
import com.monkey.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 测试自己定义图片验证码，来覆盖默认的图片验证码，一般这里写更加高级的写法，用接口就可以灵活定义自己的验证码
 * @author: monkey
 * @date: 2018/10/14 16:32
 */
//@Component("imageCodeGenerator") //先注释掉，这个类只是用于测试
public class DemoImageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("高级的图片验证码生成规则，如果这里不写，就使用默认的");
        return null;
    }
}
