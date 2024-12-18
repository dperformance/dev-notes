package com.example.devnotes.security.oauth2.repository;

import com.example.devnotes.security.oauth2.entity.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthUserRepository extends JpaRepository<OAuthUser, Long> {
    OAuthUser findByUsername(String username);
}
