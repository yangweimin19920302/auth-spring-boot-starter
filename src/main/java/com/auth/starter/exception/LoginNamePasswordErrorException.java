package com.auth.starter.exception;

/**
 * Created by Administrator on 2017/8/29.
 * 用户名或密码错误异常
 */
public class LoginNamePasswordErrorException extends RuntimeException {
    public LoginNamePasswordErrorException(String e) {
        super(e);
    }
}
