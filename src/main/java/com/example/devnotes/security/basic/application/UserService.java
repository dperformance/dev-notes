package com.example.devnotes.security.basic.application;

import com.example.devnotes.security.basic.dto.UserRegisterData;
import com.example.devnotes.security.basic.entity.User;
import com.example.devnotes.security.basic.exception.UserEmailDuplicateException;
import com.example.devnotes.security.basic.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegisterData registerData) {
        String userName = registerData.getUsername();
        if(userRepository.existsByUsername(userName)) {
            throw new UserEmailDuplicateException(userName);
        }

        User user = User.builder()
                .username(registerData.getUsername())
                .password(passwordEncoder.encode(registerData.getPassword()))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(user);

        return user;
    }
}
