package com.example.devnotes.springsecurity.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /**
                 * "/", "/login" 경로는 인증이 없어도 접근할 수 있다.
                 * /admin 경로는 login이 되어야 하고 ADMIN 권한이 있어야 한다.
                 * /my/로 시작하는 모든 URL은 "ADMIN" 또는 "USER" 권한이 있어야 한다.
                 * 위에서 정의한 매치에 해당하지 않은 모든 요청을 의미하며 이 요청에 대해서는 인증된 사용자만 접근이 가능하다.
                 */
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
