package com.monkey.security.app.validate.code.impl;

import com.monkey.security.core.validate.code.ValidateCode;
import com.monkey.security.core.validate.code.ValidateCodeException;
import com.monkey.security.core.validate.code.ValidateCodeRepository;
import com.monkey.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * app中对验证码的保存、查询、删除
 *
 * @author: monkey
 * @date: 2018/11/3 15:20
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType) {
        //设置保存验证码的时间为30，但是在生成验证码时默认配置了验证码的失效时间是60秒，所以在60秒后虽然redis中有，但是验证码已经失效了
        redisTemplate.opsForValue().set(buildKey(request, validateCodeType), validateCode, 30, TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {

        Object value = redisTemplate.opsForValue().get(buildKey(request, validateCodeType));
        return value == null ? null : (ValidateCode) value;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        redisTemplate.delete(buildKey(request, validateCodeType));
    }

    /**
     * 生成保存redis的key
     *
     * @param request
     * @param validateCodeType
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String deviceId = request.getHeader("deviceId");//从请求头中获取deviceId
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请求头中必须携带deviceId参数");
        }

        return "code:" + validateCodeType.toString().toLowerCase() + ":" + deviceId;
    }
}
