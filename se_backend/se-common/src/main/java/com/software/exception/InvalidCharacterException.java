package com.software.exception;

import com.software.constant.MessageConstant;

public class InvalidCharacterException extends BaseException {
    public InvalidCharacterException() {}

    public InvalidCharacterException(String ch, String str) {
        super("\"" + str + "\"" + MessageConstant.INVALID_CHARACTER + "'" + ch + "'");
    }
}
