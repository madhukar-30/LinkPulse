package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard API error response.")
public class ErrorResponse {

    @Schema(
            description = "Date and time when the error occurred.",
            example = "2026-07-26T15:42:18"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "HTTP status code returned by the API.",
            example = "400"
    )
    private Integer status;

    @Schema(
            description = "Short description of the error.",
            example = "Validation Failed"
    )
    private String error;

    @Schema(
            description = "Detailed error message or validation errors returned by the API.",
            example = "Original URL must be a valid URL."
    )
    private Object message;
}