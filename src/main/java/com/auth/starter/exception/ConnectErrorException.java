package com.auth.starter.exception;

/**
 * Created by Administrator on 2017/8/29.
 * 连接错误异常
 */
public class ConnectErrorException extends RuntimeException {
    public ConnectErrorException(String e) {
        super(e);
    }
}
