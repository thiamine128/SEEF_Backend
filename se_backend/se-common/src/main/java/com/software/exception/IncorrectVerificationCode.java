package com.software.exception;

public class IncorrectVerificationCode extends BaseException {
    public IncorrectVerificationCode(String msg) {
        super(msg);
    }

    public IncorrectVerificationCode() {
        super();
    }
}
