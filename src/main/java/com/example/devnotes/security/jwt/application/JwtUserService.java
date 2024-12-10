package com.example.devnotes.security.jwt.application;

import com.example.devnotes.security.basic.entity.User;
import com.example.devnotes.security.basic.repository.UserRepository;
import com.example.devnotes.security.jwt.dto.JwtRegisterRequestData;
import com.example.devnotes.security.jwt.exception.UsernameDuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtUserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public JwtUserService(UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(JwtRegisterRequestData requestData) {
      log.error(" JwtRegisterRequestData : {}", requestData.toString());

        String username = requestData.getUsername();

        if (userRepository.existsByUsername(username)) throw new UsernameDuplicateException(username);

        User user = User.builder()
                .username(requestData.getUsername())
                .password(passwordEncoder.encode(requestData.getPassword()))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(user);

        return user;

    }
}
