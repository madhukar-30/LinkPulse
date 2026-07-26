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
@Schema(description = "Response payload containing information about a shortened link.")
public class LinkResponse {

    @Schema(
            description = "Unique identifier of the shortened link.",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Original destination URL.",
            example = "https://example.com/articles/linkpulse"
    )
    private String originalUrl;

    @Schema(
            description = "Unique short code generated for the shortened URL.",
            example = "aB3xYz89"
    )
    private String shortCode;

    @Schema(
            description = "Total number of recorded clicks.",
            example = "42"
    )
    private Long clickCount;

    @Schema(
            description = "Date and time when the shortened link was created.",
            example = "2026-07-26T15:42:18"
    )
    private LocalDateTime createdAt;
}