package com.example.devnotes.security.jwt.controller;

import com.example.devnotes.security.jwt.application.JwtUserService;
import com.example.devnotes.security.jwt.dto.JwtRegisterRequestData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt/users")
public class JwtUserController {

    private final JwtUserService userService;

    public JwtUserController(JwtUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody JwtRegisterRequestData requestData) {
        return ResponseEntity.ok(userService.registerUser(requestData));
    }
}
