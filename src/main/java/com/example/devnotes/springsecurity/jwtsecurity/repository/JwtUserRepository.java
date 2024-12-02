package com.example.devnotes.springsecurity.jwtsecurity.repository;

import com.example.devnotes.springsecurity.jwtsecurity.entity.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtUserRepository extends JpaRepository<JwtUser, Long> {
}
