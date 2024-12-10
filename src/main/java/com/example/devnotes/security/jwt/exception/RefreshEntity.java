package com.example.devnotes.security.jwt.exception;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class RefreshEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String refresh;
    private String expiration;

    @Builder
    public RefreshEntity(Long id, String username, String refresh, String expiration) {
        this.id = id;
        this.username = username;
        this.refresh = refresh;
        this.expiration = expiration;
    }
}
