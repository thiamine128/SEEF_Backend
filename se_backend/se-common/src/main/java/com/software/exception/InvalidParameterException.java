package com.software.exception;

public class InvalidParameterException extends BaseException {
    public InvalidParameterException() {
        super();
    }
    public InvalidParameterException(String msg) {
        super(msg);
    }
}
