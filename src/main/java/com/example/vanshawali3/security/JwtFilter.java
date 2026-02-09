package com.example.vanshawali3.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // 🔓 Public APIs → JWT check SKIP
        if (path.contains("/auth/login") || path.contains("/signupuser")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("JWT FILTER HIT");

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("JWT HEADER = " + request.getHeader("Authorization"));


        String token = authHeader.substring(7);

        Claims claims = jwtService.extractClaims(token);

        String username = claims.getSubject();
        
        Long signupUserId = claims.get("userId", Long.class);

     // 🔥 attach to request
        request.setAttribute("signupUserId", signupUserId);
        

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username, null, List.of()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}