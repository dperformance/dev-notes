package com.example.devnotes.global.error;

import com.example.devnotes.global.error.exception.BusinessException;
import com.example.devnotes.global.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*
    *
    */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }


    // Spring Security 인증 예외 처리
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    protected ResponseEntity<ErrorResponse> handleInternalAuthenticationServiceException(
            InternalAuthenticationServiceException e) {
        ErrorResponse response = ErrorResponse.of(
                ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // UsernameNotFoundException도 따로 처리하고 싶다면
    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUsernameNotFoundException(
            UsernameNotFoundException e) {
        ErrorResponse response = ErrorResponse.of(
                ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
