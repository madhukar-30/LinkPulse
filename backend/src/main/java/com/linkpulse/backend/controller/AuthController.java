package com.linkpulse.backend.controller;


import com.linkpulse.backend.dto.LoginRequest;
import com.linkpulse.backend.dto.LoginResponse;
import com.linkpulse.backend.dto.RegisterRequest;
import com.linkpulse.backend.dto.RegisterResponse;
import com.linkpulse.backend.service.AuthService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
