package com.monkey.security.app;

/**
 * 自定义app的异常
 * @author: monkey
 * @date: 2018/11/4 12:03
 */
public class AppSecretException extends RuntimeException {

    public AppSecretException(String message) {
        super(message);
    }
}
