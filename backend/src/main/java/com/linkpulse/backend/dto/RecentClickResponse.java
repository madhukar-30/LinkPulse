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
@Schema(description = "Response payload containing information about a recent click event.")
public class RecentClickResponse {

    @Schema(
            description = "Date and time when the click event was recorded.",
            example = "2026-07-26T15:42:18"
    )
    private LocalDateTime clickedAt;

    @Schema(
            description = "IP address of the visitor.",
            example = "203.0.113.10"
    )
    private String ipAddress;

    @Schema(
            description = "Raw User-Agent header sent by the visitor.",
            example = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/138.0.0.0 Safari/537.36"
    )
    private String userAgent;

    @Schema(
            description = "Name of the detected web browser.",
            example = "Chrome"
    )
    private String browser;

    @Schema(
            description = "Name of the detected operating system.",
            example = "Windows"
    )
    private String operatingSystem;

    @Schema(
            description = "Detected device type.",
            example = "Desktop"
    )
    private String deviceType;

    @Schema(
            description = "Referrer URL sent by the visitor.",
            example = "https://google.com/"
    )
    private String referrer;
}