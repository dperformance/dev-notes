package com.example.devnotes.security.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth/my")
public class OauthMyController {

    @GetMapping
    public String myPage() {
        return "oauthMy";
    }
}
