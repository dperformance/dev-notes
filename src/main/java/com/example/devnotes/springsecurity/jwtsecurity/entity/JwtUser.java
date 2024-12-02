package com.example.devnotes.springsecurity.jwtsecurity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "jwt_member")
public class JwtUser {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String password;

    private String role;

    @Builder
    public JwtUser(long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
