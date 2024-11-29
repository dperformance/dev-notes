package com.example.devnotes.springsecurity.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
                        .requestMatchers("/", "/login", "/loginProc", "/users", "/profile").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()  // H2 콘솔 접근 허용
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/profile/**").authenticated()
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );


        http
                /**
                 * 사용자가 인증이 필요한 URL 에 접근할 때 인증되지 않은 경우, Security 는 /login URL 로 리다이렉트시킨다.
                 * loginProcessingUrl("loginProc)
                 *   - login 요청을 할때 사용할 URL 을 설정한다.
                 *   - Security 는 이 URL을 통해 로그인 요청을 처리하고, 인증을 수행한다.
                 */
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        // 개발 환경에서는 csrf를 disable해준다 login form 에 csrf token이 같이 서버로 전송되어야 하기 때문에 현재는 disabled 처리한다.
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")  // H2 콘솔은 CSRF 검사에서 제외
                        .disable()
                );

        http
                .headers(headers -> headers
                        .frameOptions(frameOption -> frameOption.disable())
                );

        return http.build();
    }
}
