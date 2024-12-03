package com.example.devnotes.security.jwt.config;

import com.example.devnotes.security.jwt.filter.JwtFilter;
import com.example.devnotes.security.jwt.jwt.LoginFilter;
import com.example.devnotes.security.jwt.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JwtUtil jwtUtil;

    public JwtSecurityConfig(AuthenticationConfiguration authenticationConfiguration,
                             JwtUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * H2 Console 설정
         * 1. H2 콘솔은 CSRF 토큰을 지원하지 않으므로 비활성화 시켜야 함.
         *    - 나중에 배포시에는 .csrf(csrf -> csrf
         *                        .ignoringRequestMatchers("/h2-console/**")
         *                        )
         *      설정을 해줘야 함.
         * 2. frameOptions 비활성화
         *    - H2 콘솔은 iframe 내에서 실행되는데 Spring Security 는 보안을 위해 iframe 사용을 차단
         *    - 비활성화하지 않으면 콘솔 화면이 표시되지 않음
         */
        http
                .csrf(AbstractHttpConfigurer::disable)// 1. CSRF 보호 비활성화
                .headers(headers -> headers // 2. X-Frame-Options 헤더 비활성화 (iframe 허용)
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/h2-console/**").permitAll() // 3. H2 콘솔 경로 허용
                );

        http
                .formLogin((auth) -> auth.disable()) // Form 로그인 방식 disable
                .httpBasic((auth) -> auth.disable()) // http basic 인증 방식 disable
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/jwt/login", "/jwt/users", "/jwt/main").permitAll() // 공개 URL
                        .requestMatchers("/jwt/admin").hasRole("ADMIN") // 관리자 전용
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)
                //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 미사용

        return http.build();
    }
}
