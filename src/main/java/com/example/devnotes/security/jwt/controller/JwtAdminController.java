package com.example.devnotes.security.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt/admin")
public class JwtAdminController {

    @GetMapping
    public String adminPage() {
        return "admin Controller";
    }
}
