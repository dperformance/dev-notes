package com.example.devnotes.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class GlobalSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * devnotes에는 여러가지 공부내용이 혼합될 예정이기 때문에
     * security, jwt, oauth2 가 이미 구성되어 있어 security 의존성을 제외 시킬 수 없다.
     * 그렇기 때문에 security filterchain 설정을 추가해서 모든 요청에 한해 permitAll 시킨다
     */


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll());

        return http.build();
    }
}
