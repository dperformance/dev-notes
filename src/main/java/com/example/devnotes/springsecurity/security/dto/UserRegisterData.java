package com.example.devnotes.springsecurity.security.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
public class UserRegisterData {

    private String username;

    private String password;

}
