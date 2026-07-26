package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request payload for user registration.")
public class RegisterRequest {

    @Schema(
            description = "Display name of the user.",
            example = "Alex Johnson"
    )
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(
            description = "Email address of the user.",
            example = "alex@example.com"
    )
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(
            description = "Password for the user account.",
            example = "SecurePassword123!"
    )
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}