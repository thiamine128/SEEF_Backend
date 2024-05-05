package com.software.exception;

public class NoSuchTopicException extends BaseException {
    public NoSuchTopicException(String msg) {
        super(msg);
    }

    public NoSuchTopicException() {
        super();
    }
}
