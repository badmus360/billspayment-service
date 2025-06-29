package com.fintech.billspayment.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecretKeyAuthFilter extends OncePerRequestFilter {

    @Value("${app.secret-key}")
    private String validSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String requestKey = request.getHeader("X-API-KEY");

//        if (requestKey == null) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API Key required");
//            return;
//        }
//
//        if (!requestKey.equals(validSecretKey)) {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid API Key");
//            return;
//        }

        filterChain.doFilter(request, response);
    }
}