package com.example.devnotes.security.jwt.controller;


import com.example.devnotes.security.jwt.application.TokenService;
import com.example.devnotes.security.jwt.dto.TokenResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt/reissue")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenResponseData> refreshAccessToken(HttpServletRequest request,
                                                                HttpServletResponse response) {
        return ResponseEntity.ok(tokenService.refreshAccessToken(request, response));
    }
}
