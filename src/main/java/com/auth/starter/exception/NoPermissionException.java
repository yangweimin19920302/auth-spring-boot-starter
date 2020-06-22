package com.auth.starter.exception;

/**
 * Created by Administrator on 2017/8/29.
 * 没有权限异常
 */
public class NoPermissionException extends Exception {
    public NoPermissionException(String e) {
        super(e);
    }
}
