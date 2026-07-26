package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Analytics summary for a shortened link.")
public class AnalyticsResponse {

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
            description = "Date and time when the shortened link was created.",
            example = "2025-07-20T14:30:15"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Total number of recorded clicks.",
            example = "42"
    )
    private Long totalClicks;

    @Schema(
            description = "Daily click statistics for the shortened link."
    )
    private List<DailyClickResponse> dailyClicks;

    @Schema(
            description = "Click statistics grouped by browser."
    )
    private List<BrowserStatResponse> browserStats;

    @Schema(
            description = "Click statistics grouped by operating system."
    )
    private List<OperatingSystemStatResponse> operatingSystemStats;

    @Schema(
            description = "Most recent click events recorded for the shortened link."
    )
    private List<RecentClickResponse> recentClicks;
}