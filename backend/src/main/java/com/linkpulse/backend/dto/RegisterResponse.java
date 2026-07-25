package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Registration result")
public class RegisterResponse {

    @Schema(
            description = "Registration status message",
            example = "User registered successfully"
    )
    private final String message;
}