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
@Schema(description = "API error response")
public class ErrorResponse {

    @Schema(description = "Date and time when the error occurred")
    private LocalDateTime timestamp;

    @Schema(
            description = "HTTP status code",
            example = "400"
    )
    private Integer status;

    @Schema(
            description = "Error category",
            example = "Validation Failed"
    )
    private String error;

    @Schema(
            description = "Error details or validation messages"
    )
    private Object message;
}