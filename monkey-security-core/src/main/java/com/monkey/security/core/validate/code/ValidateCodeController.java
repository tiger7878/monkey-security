package com.monkey.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 验证码的控制器
 * @author: monkey
 * @date: 2018/10/13 15:33
 */
@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    /**
     * 依赖查找：收集系统中所有的 ValidateCodeProcessor 接口的实现，key是名字
     */
    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessors;

    /**
     *  获取验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {

        validateCodeProcessors.get(type+"CodeProcessor").create(new ServletWebRequest(request,response));

    }


}
