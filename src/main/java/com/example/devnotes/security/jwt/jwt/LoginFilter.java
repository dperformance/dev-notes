package com.example.devnotes.security.jwt.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        /**
         * LoginFilter는 UsernamePasswordAuthenticationFilter를 상속받았기 때문에, 기본적으로 /login POST 요청을 처리한다.
         * 하지만 현재 프로젝트는 /jwt/login을 permitAll()로 설정했으므로, 필터의 처리 URL을 변경해야 한다.
         */
        setFilterProcessesUrl("/jwt/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        /**
         *
         * 클라이언트 요청에서 username, password 추출
         * 우리가 구현한 UsernamePasswordAuthenticationFilter 에서 AuthenticationManager 에 던져줘야 하는데
         * 이때 기냥 던지면 안되고, UsernamePasswordAuthenticationToken(DTO)에 담아서 던져줘야 한다.
         */
        if ("application/json".equals(request.getContentType())) {
            try {
                // ObjectMapper를 사용하여 JSON 데이터를 파싱
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> requestMap = objectMapper.readValue(request.getInputStream(), Map.class);

                String username = requestMap.get("username");
                String password = requestMap.get("password");

                // UsernamePasswordAuthenticationToken 생성
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

                // AuthenticationManager로 전달
                return authenticationManager.authenticate(authToken);

            } catch (IOException e) {
                throw new AuthenticationServiceException("Failed to parse authentication request body", e);
            }

        } else {
            String username = obtainUsername(request);
            String password = obtainPassword(request);

            // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            // token에 담은 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);
        }
    }

    // 로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("성공");
    }

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("실패");
    }
}
