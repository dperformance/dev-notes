package com.example.devnotes.security.jwt.repository;

import com.example.devnotes.global.error.exception.ErrorCode;
import com.example.devnotes.global.error.exception.InvalidValueException;

public class UsernameDuplicateException extends InvalidValueException {
    public UsernameDuplicateException(String username) {
        super(username, ErrorCode.USER_NAME_DUPLICATION);
    }
}
