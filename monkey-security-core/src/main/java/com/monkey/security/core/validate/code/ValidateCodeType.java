package com.monkey.security.core.validate.code;

import com.monkey.security.core.properties.SecurityConstants;

/**
 * 验证码类型
 * 它设计得很好，很多地方都用到了它
 * @author: monkey
 * @date: 2018/10/21 11:44
 */
public enum  ValidateCodeType {

    /**
     * 短信验证码
     */
    SMS{
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },

    /**
     * 图片验证码
     */
    IMAGE{
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };


    /**
     * 校验时从请求中获取的参数的名字
     * 比如：imageCode和smsCode
     * @return
     */
    public abstract String getParamNameOnValidate();
}
