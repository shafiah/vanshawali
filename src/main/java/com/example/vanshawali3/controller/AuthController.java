package com.example.vanshawali3.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vanshawali3.dto.LoginRequest;
import com.example.vanshawali3.dto.LoginResponse;
import com.example.vanshawali3.entities.SignUpUser;
import com.example.vanshawali3.repositories.SignUpUserRepository;
import com.example.vanshawali3.security.JwtService;

@RestController
@RequestMapping("/api/v5/vanshawali/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private SignUpUserRepository signUpUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        SignUpUser user = signUpUserRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(
                new LoginResponse(token, user.getUsername())
        );
    }
}