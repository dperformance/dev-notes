package com.example.devnotes.security.jwt.filter;

import com.example.devnotes.security.basic.dto.CustomUserDetails;
import com.example.devnotes.security.basic.entity.User;
import com.example.devnotes.security.jwt.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /**
         * 일반 Jwt 로그인시 로직
         */
//        // reqeust에서 Authorization 헤더를 찾음
//        String authorization = request.getHeader("Authorization");
//
//        // Authorization 헤더 검증
//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//            System.out.println("Jwt Token Null");
//            filterChain.doFilter(request,response);
//            return; // 조건이 해당되면 메소드 종료 (필수)
//        }
//
//        // Bearer 부분 제거 후 순수 토큰만 획득
//        String token = authorization.split(" ")[1];
//
//        // 토큰 소멸 시간 검증
//        if (jwtUtil.isExpired(token)) {
//            System.out.println("Jwt Token expired");
//            filterChain.doFilter(request,response);
//            return; // 조건이 해당되면 메소드 종료 (필수)
//        }
//
//        // 토큰에서 username과 role획득
//        String username = jwtUtil.getUsername(token);
//        String role = jwtUtil.getRole(token);
//
//        // User 객체를 생성
//        User user = User.builder()
//                .username(username)
//                .password("temppassword")
//                .role(role)
//                .build();
//
//        // UserDetails에 회원 정보 객체 담기
//        CustomUserDetails customUserDetails = new CustomUserDetails(user);
//
//        // 스프링 시큐리티 인증 토큰 생성
//        UsernamePasswordAuthenticationToken authToken =
//                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//
//        // 세션에 사용자 등록
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        filterChain.doFilter(request,response);

        /**
         * Access, Refresh Token 을 발급하는 로직 (JWT 심화)
         */
        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        if (accessToken == null) {
            filterChain.doFilter(request,response);
            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            // 클라이언트와 상의된 HTTP Status code를 응답준다.
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // 토큰이 access 인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);
        if (!category.equals("access")) {
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            // 클라이언트와 상의된 HTTP Status Code를 응답준다.
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // username, role 값을 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        User user = User.builder()
                .username(username)
                .role(role)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken =
                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request,response);
    }
}
