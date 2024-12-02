package com.example.devnotes.security.basic.controller;

import com.example.devnotes.security.basic.application.UserService;
import com.example.devnotes.security.basic.dto.UserRegisterData;
import com.example.devnotes.security.basic.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registerPage() {
        return "register";
    }

    @PostMapping
    public ResponseEntity<User> create(
            @RequestBody UserRegisterData registerData) {
        log.info("registerData : {}",registerData);
        return ResponseEntity.ok(userService.registerUser(registerData));
    }
}
