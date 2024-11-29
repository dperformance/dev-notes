package com.example.devnotes.global.error.exception;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String message) {
        super(message, ErrorCode.PRODUCT_NOT_FOUND);
    }

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
