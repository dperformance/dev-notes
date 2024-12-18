package com.example.devnotes.security.oauth2.config;

import com.example.devnotes.security.oauth2.application.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OAuthSecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public OAuthSecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         //         * H2 Console 설정
         //         * 1. H2 콘솔은 CSRF 토큰을 지원하지 않으므로 비활성화 시켜야 함.
         //         *    - 나중에 배포시에는 .csrf(csrf -> csrf
         //         *                        .ignoringRequestMatchers("/h2-console/**")
         //         *                        )
         //         *      설정을 해줘야 함.
         //         * 2. frameOptions 비활성화
         //         *    - H2 콘솔은 iframe 내에서 실행되는데 Spring Security 는 보안을 위해 iframe 사용을 차단
         //         *    - 비활성화하지 않으면 콘솔 화면이 표시되지 않음
         //         */
        http
                .csrf(AbstractHttpConfigurer::disable)// 1. CSRF 보호 비활성화
                .headers(headers -> headers // 2. X-Frame-Options 헤더 비활성화 (iframe 허용)
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/h2-console/**").permitAll() // 3. H2 콘솔 경로 허용
                );

        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
//                .oauth2Login(Customizer.withDefaults());
                .oauth2Login((oauth) -> oauth
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/oauth/login/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
