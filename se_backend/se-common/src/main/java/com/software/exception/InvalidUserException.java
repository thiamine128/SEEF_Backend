package com.software.exception;

public class InvalidUserException extends BaseException {
    public InvalidUserException() {

    }

    public InvalidUserException(String msg) {
        super(msg);
    }
}
