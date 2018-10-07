package com.monkey.web.async;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于两个线程之间传递DeferredResult对象
 * @author: monkey
 * @date: 2018/10/7 11:54
 */
@Component
public class DeferredResultHolder {

    private Map<String, DeferredResult<String>> map=new HashMap<>();

    public Map<String, DeferredResult<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult<String>> map) {
        this.map = map;
    }
}
