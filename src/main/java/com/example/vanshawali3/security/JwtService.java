package com.example.vanshawali3.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.vanshawali3.entities.SignUpUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY =
            "VANSHAWALI_SECRET_KEY_12345678901234567890";

    private final byte[] keyBytes =
            SECRET_KEY.getBytes(StandardCharsets.UTF_8);

    public String generateToken(SignUpUser user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getSignUpUserId())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 86400000)
                )
                .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}