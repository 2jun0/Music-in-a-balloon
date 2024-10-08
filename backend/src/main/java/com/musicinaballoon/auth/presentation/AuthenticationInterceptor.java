package com.musicinaballoon.auth.presentation;

import com.musicinaballoon.auth.AuthenticationContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthenticationContext context;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getCookies() == null) {
            return true;
        }

        for (var cookie : request.getCookies()) {
            if (cookie.getName().equals("userId")) {
                context.setUserId(Long.parseLong(cookie.getValue()));
            }
        }

        return true;
    }
}
