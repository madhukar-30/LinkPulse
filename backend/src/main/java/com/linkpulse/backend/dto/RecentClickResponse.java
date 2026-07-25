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
@Schema(description = "Recorded click event")
public class RecentClickResponse {

    @Schema(description = "Date and time when the click occurred")
    private LocalDateTime clickedAt;

    @Schema(
            description = "Visitor IP address",
            example = "203.0.113.10"
    )
    private String ipAddress;

    @Schema(description = "Raw User-Agent header sent by the visitor")
    private String userAgent;

    @Schema(
            description = "Detected browser",
            example = "Chrome"
    )
    private String browser;

    @Schema(
            description = "Detected operating system",
            example = "Windows"
    )
    private String operatingSystem;

    @Schema(
            description = "Detected device type",
            example = "Desktop"
    )
    private String deviceType;

    @Schema(
            description = "Referrer URL sent by the visitor",
            example = "https://google.com/"
    )
    private String referrer;
}