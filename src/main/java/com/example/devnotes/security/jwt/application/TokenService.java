package com.example.devnotes.security.jwt.application;

import com.example.devnotes.security.jwt.dto.TokenResponseData;
import com.example.devnotes.security.jwt.exception.TokenException;
import com.example.devnotes.security.jwt.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;

    public TokenResponseData refreshAccessToken(HttpServletRequest request,
                                                HttpServletResponse response) {
        // 1. Refresh 토큰 추출
        String refreshToken = extractRefreshToken(request);
        if (refreshToken == null) {
            throw new TokenException("Refresh token not found");
        }

        // 2. Refresh 토큰 검증
        try {
            jwtUtil.isExpired(refreshToken);

            // Refresh 토큰 타입 확인
            if (!"refresh".equals(jwtUtil.getCategory(refreshToken))) {
                throw new TokenException("Invalid refresh token");
            }
        } catch (ExpiredJwtException e) {
            throw new TokenException("Refresh token expired");
        }

        // 3. 새로운 Access 토큰 생성
        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);
        String newAccessToken = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        // 4. 응답 헤더에 새로운 Access 토큰 설정
        response.setHeader("access", newAccessToken);
        response.addCookie(createCookie(newRefresh));

        return new TokenResponseData("Access token refreshed successfully", "refresh");
    }

    private String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if ("refresh".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private Cookie createCookie(String value) {
        Cookie cookie = new Cookie("refresh", value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}

