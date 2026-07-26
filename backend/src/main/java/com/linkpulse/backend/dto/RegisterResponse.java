package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Response payload containing the result of a successful user registration.")
public class RegisterResponse {

    @Schema(
            description = "Confirmation message indicating that the user was registered successfully.",
            example = "User registered successfully"
    )
    private final String message;
}