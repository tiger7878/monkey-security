package com.monkey.exception;

/**
 * 自定义异常
 * @author: monkey
 * @date: 2018/10/6 15:11
 */
public class UserNotExistException extends RuntimeException {

    private String id;//用来保存用户信息的

    public UserNotExistException(String id) {
        super("user not exist");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
