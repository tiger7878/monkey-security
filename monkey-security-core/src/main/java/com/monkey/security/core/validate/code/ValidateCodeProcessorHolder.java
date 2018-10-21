package com.monkey.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 验证码处理器的控制器
 *
 * @author: monkey
 * @date: 2018/10/21 14:58
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 根据验证码类型获取对应的处理器
     *
     * @param validateCodeType
     * @return
     */
    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType validateCodeType) {
        return findValidateCodeProcessor(validateCodeType.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();

        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessors.get(name);

        if (validateCodeProcessor == null) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }

        return validateCodeProcessor;
    }

}
