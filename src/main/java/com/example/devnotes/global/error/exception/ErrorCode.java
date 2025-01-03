package com.example.devnotes.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400,"C001", "Invalid Input Value"),
    INVALID_TOKEN_VALUE(401, "C002", "Invalid Token Value"),

    // Product
    PRODUCT_NOT_FOUND(404, "P001", "Product Not Found"),

    EMAIL_FAIL(400, "P002", "Login fail "),

    // User
    USER_NOT_FOUND(404, "U001", "User Not Found"),
    EMAIL_DUPLICATION(400, "U002", "Email is Duplication"),
    USER_NAME_DUPLICATION(400, "U003", "Username is Duplication");

    private int status;

    private String code;

    private String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
