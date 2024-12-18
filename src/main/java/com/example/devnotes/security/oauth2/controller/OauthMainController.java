package com.example.devnotes.security.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauth")
public class OauthMainController {

    @GetMapping
    public String mainPage() {
        return "oauthMain";
    }

    @GetMapping("/new/login")
    public String newLoginPage() {
        return "oauthLogin";
    }
}
