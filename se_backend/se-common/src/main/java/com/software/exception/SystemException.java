package com.software.exception;

/**
 * 账号不存在异常
 */
public class SystemException extends BaseException {

    public SystemException() {
    }

    public SystemException(String msg) {
        super(msg);
    }

}
