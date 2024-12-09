package com.example.devnotes.security.jwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TokenResponseData {

    private String message;

    private LocalDateTime timestamp;

    private String tokenType;

    @Builder
    public TokenResponseData(String message, String tokenType) {
        this.message = message;
        this.tokenType = tokenType;
        this.timestamp = LocalDateTime.now();
    }
}
