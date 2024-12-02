package com.example.devnotes.security.jwt.dto;

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
