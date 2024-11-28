package com.example.devnotes.springsecurity.security.repository;

import com.example.devnotes.springsecurity.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
}
