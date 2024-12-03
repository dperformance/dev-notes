package com.example.devnotes.security.jwt.filter;

import com.example.devnotes.security.basic.dto.CustomUserDetails;
import com.example.devnotes.security.basic.entity.User;
import com.example.devnotes.security.jwt.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // reqeust에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("Jwt Token Null");
            filterChain.doFilter(request,response);
            return; // 조건이 해당되면 메소드 종료 (필수)
        }

        // Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("Jwt Token expired");
            filterChain.doFilter(request,response);
            return; // 조건이 해당되면 메소드 종료 (필수)
        }

        // 토큰에서 username과 role획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // User 객체를 생성
        User user = User.builder()
                .username(username)
                .password("temppassword")
                .role(role)
                .build();

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 스프링 시큐리티 인증 토큰 생성
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }
}
