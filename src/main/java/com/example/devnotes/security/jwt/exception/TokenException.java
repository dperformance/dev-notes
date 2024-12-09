package com.example.devnotes.security.jwt.exception;

import com.example.devnotes.global.error.exception.BusinessException;
import com.example.devnotes.global.error.exception.ErrorCode;

public class TokenException extends BusinessException {
    public TokenException(String message) {
        super(message, ErrorCode.INVALID_TOKEN_VALUE);
    }
}
