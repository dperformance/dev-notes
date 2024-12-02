package com.example.devnotes.security.basic.exception;

import com.example.devnotes.global.error.exception.ErrorCode;
import com.example.devnotes.global.error.exception.InvalidValueException;

public class InvalidTokenException extends InvalidValueException {

    public InvalidTokenException(String token) {
        super(token, ErrorCode.INVALID_TOKEN_VALUE);
    }
}
