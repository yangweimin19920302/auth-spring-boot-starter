package com.auth.starter.exception;

/**
 * Created by Administrator on 2017/8/29.
 * token过期异常
 */
public class OutOfDateException extends RuntimeException {
    public OutOfDateException(String e) {
        super(e);
    }
}
