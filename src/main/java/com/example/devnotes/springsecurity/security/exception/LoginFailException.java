package com.example.devnotes.springsecurity.security.exception;

import com.example.devnotes.global.error.exception.ErrorCode;
import com.example.devnotes.global.error.exception.InvalidValueException;

public class LoginFailException extends InvalidValueException {
    public LoginFailException(String email) {
        super("login fail - email : " + email, ErrorCode.EMAIL_FAIL);
    }
}