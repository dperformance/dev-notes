package com.example.devnotes.security.jwt.filter;

import com.example.devnotes.security.jwt.repository.RefreshRepository;
import com.example.devnotes.security.jwt.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public CustomLogoutFilter(JwtUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // 경로 및 메소드 검증
            if (!isLogoutRequest(request)) {
                chain.doFilter(request, response);
                return;
            }

            // 리프레시 토큰 추출 및 검증
            String refreshToken = extractAndValidateRefreshToken(request, response);
            if (refreshToken == null) {
                return;
            }

            // 로그아웃 처리
            processLogout(refreshToken, response);

        } catch (Exception e) {
            log.error("Logout process failed", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "로그아웃 처리 중 오류가 발생했습니다.");
        }
    }

    private boolean isLogoutRequest(HttpServletRequest request) {
        return request.getRequestURI().equals("/logout")
                && request.getMethod().equals("POST");
    }

    private String extractAndValidateRefreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 쿠키 null 체크
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "쿠키가 존재하지 않습니다.");
            return null;
        }

        // 리프레시 토큰 추출
        String refreshToken = null;
        for (Cookie cookie : cookies) {
            if ("refresh".equals(cookie.getName())) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if (refreshToken == null) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "리프레시 토큰이 없습니다.");
            return null;
        }

        // 토큰 검증
        try {
            jwtUtil.isExpired(refreshToken);

            if (!"refresh".equals(jwtUtil.getCategory(refreshToken))) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "유효하지 않은 토큰입니다.");
                return null;
            }

            if (!refreshRepository.existsByRefresh(refreshToken)) {
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "존재하지 않는 토큰입니다.");
                return null;
            }

        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "만료된 토큰입니다.");
            return null;
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "토큰 검증 중 오류가 발생했습니다.");
            return null;
        }

        return refreshToken;
    }

    private void processLogout(String refreshToken, HttpServletResponse response) throws IOException {
        try {
            // DB에서 토큰 제거
            refreshRepository.deleteByRefresh(refreshToken);

            // 쿠키 제거
            response.addCookie(createLogoutCookie());

            // 성공 응답
            sendSuccessResponse(response);

        } catch (Exception e) {
            log.error("Error during logout process", e);
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "로그아웃 처리 중 오류가 발생했습니다.");
        }
    }

    private Cookie createLogoutCookie() {
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        return cookie;
    }

    private void sendSuccessResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\": \"로그아웃이 성공적으로 처리되었습니다.\"}");
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format("{\"message\": \"%s\"}", message));
    }
}
