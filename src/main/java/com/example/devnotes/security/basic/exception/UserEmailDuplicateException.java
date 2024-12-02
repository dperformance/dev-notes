package com.example.devnotes.security.basic.exception;

import com.example.devnotes.global.error.exception.ErrorCode;
import com.example.devnotes.global.error.exception.InvalidValueException;

public class UserEmailDuplicateException extends InvalidValueException {

    public UserEmailDuplicateException(String email) {
        super(email, ErrorCode.EMAIL_DUPLICATION);
    }
}
