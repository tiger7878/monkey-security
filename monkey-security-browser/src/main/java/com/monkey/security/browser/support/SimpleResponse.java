package com.monkey.security.browser.support;

/**
 * 简单的返回对象
 * @author: monkey
 * @date: 2018/10/9 21:50
 */
public class SimpleResponse {

    public SimpleResponse(Object content) {
        this.content = content;
    }

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
