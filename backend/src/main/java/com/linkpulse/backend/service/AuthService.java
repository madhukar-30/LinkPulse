package com.linkpulse.backend.service;

import com.linkpulse.backend.dto.LoginRequest;
import com.linkpulse.backend.dto.LoginResponse;
import com.linkpulse.backend.dto.RegisterRequest;
import com.linkpulse.backend.dto.RegisterResponse;
import com.linkpulse.backend.entity.Role;
import com.linkpulse.backend.entity.User;
import com.linkpulse.backend.exception.InvalidCredentialsException;
import com.linkpulse.backend.exception.UserAlreadyExistsException;
import com.linkpulse.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .isVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return new RegisterResponse("User registered successfully");
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String accessToken = jwtService.generateToken(user);

        return new LoginResponse(accessToken);
    }
}