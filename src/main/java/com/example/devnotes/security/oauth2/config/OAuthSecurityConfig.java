//package com.example.devnotes.security.oauth2.config;
//
//import com.example.devnotes.security.oauth2.application.CustomOAuth2UserService;
//import com.example.devnotes.security.oauth2.oauth2.CustomClientRegistrationRepo;
//import com.example.devnotes.security.oauth2.oauth2.CustomOAuth2AuthorizedClientService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class OAuthSecurityConfig {
//
//    private final CustomOAuth2UserService customOAuth2UserService;
//
//    private final CustomClientRegistrationRepo customClientRegistrationRepo;
//
//    private final CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public OAuthSecurityConfig(CustomOAuth2UserService customOAuth2UserService,
//                               CustomClientRegistrationRepo customClientRegistrationRepo,
//                               CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService,
//                               JdbcTemplate jdbcTemplate) {
//        this.customOAuth2UserService = customOAuth2UserService;
//        this.customClientRegistrationRepo = customClientRegistrationRepo;
//        this.customOAuth2AuthorizedClientService = customOAuth2AuthorizedClientService;
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        /**
//         //         * H2 Console 설정
//         //         * 1. H2 콘솔은 CSRF 토큰을 지원하지 않으므로 비활성화 시켜야 함.
//         //         *    - 나중에 배포시에는 .csrf(csrf -> csrf
//         //         *                        .ignoringRequestMatchers("/h2-console/**")
//         //         *                        )
//         //         *      설정을 해줘야 함.
//         //         * 2. frameOptions 비활성화
//         //         *    - H2 콘솔은 iframe 내에서 실행되는데 Spring Security 는 보안을 위해 iframe 사용을 차단
//         //         *    - 비활성화하지 않으면 콘솔 화면이 표시되지 않음
//         //         */
//        http
//                .csrf(AbstractHttpConfigurer::disable)// 1. CSRF 보호 비활성화
//                .headers(headers -> headers // 2. X-Frame-Options 헤더 비활성화 (iframe 허용)
//                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/h2-console/**").permitAll() // 3. H2 콘솔 경로 허용
//                );
//
//        http
//                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
////                .oauth2Login(Customizer.withDefaults());
//                .oauth2Login((oauth) -> oauth
//                        .loginPage("/oauth/new/login")
//                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
//                        .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(
//                                jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository())
//                        )
//                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
//                                .userService(customOAuth2UserService))
//                        .defaultSuccessUrl("/oauth", true)); // GET 방식으로 리다이렉트를 수행
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/", "/oauth2/**", "/oauth/login/**", "/oauth/**").permitAll()
//                        .anyRequest().authenticated());
//
//        return http.build();
//    }
//}
