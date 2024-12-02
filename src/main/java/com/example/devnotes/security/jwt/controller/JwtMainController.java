package com.example.devnotes.security.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt/main")
public class JwtMainController {

    @GetMapping
    public String mainPage() {
        return "main Controller";
    }
}
