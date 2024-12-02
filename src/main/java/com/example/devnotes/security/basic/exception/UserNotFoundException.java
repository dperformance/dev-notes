package com.example.devnotes.security.basic.exception;

import com.example.devnotes.global.error.exception.EntityNotFoundException;
import com.example.devnotes.global.error.exception.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String username) {
        super(username + "User Not Found ", ErrorCode.USER_NOT_FOUND);
    }
}
