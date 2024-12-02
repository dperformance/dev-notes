package com.example.devnotes.springsecurity.jwtsecurity.application;

import com.example.devnotes.springsecurity.jwtsecurity.dto.JwtRegisterRequestData;
import com.example.devnotes.springsecurity.jwtsecurity.entity.JwtUser;
import com.example.devnotes.springsecurity.jwtsecurity.repository.JwtUserRepository;
import com.example.devnotes.springsecurity.jwtsecurity.repository.UsernameDuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtUserService {

    private final JwtUserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public JwtUserService(JwtUserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtUser registerUser(JwtRegisterRequestData requestData) {
      log.error(" JwtRegisterRequestData : {}", requestData.toString());

        String username = requestData.getUsername();

        if (userRepository.existsByUsername(username)) throw new UsernameDuplicateException(username);

        JwtUser user = JwtUser.builder()
                .username(requestData.getUsername())
                .password(passwordEncoder.encode(requestData.getPassword()))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(user);

        return user;

    }
}
