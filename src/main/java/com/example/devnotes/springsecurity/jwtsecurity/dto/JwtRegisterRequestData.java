package com.example.devnotes.springsecurity.jwtsecurity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class JwtRegisterRequestData {

    private String username;

    private String password;

}
