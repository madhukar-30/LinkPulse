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
@Schema(description = "Analytics summary for a shortened link")
public class AnalyticsResponse {

    @Schema(
            description = "Unique identifier of the link",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Original destination URL",
            example = "https://example.com/articles/linkpulse"
    )
    private String originalUrl;

    @Schema(
            description = "Generated shortened URL code",
            example = "aB3xYz89"
    )
    private String shortCode;

    @Schema(description = "Date and time when the link was created")
    private LocalDateTime createdAt;

    @Schema(
            description = "Total number of recorded clicks",
            example = "42"
    )
    private Long totalClicks;

    @Schema(description = "Click counts grouped by day")
    private List<DailyClickResponse> dailyClicks;

    @Schema(description = "Click counts grouped by browser")
    private List<BrowserStatResponse> browserStats;

    @Schema(description = "Click counts grouped by operating system")
    private List<OperatingSystemStatResponse> operatingSystemStats;

    @Schema(description = "Most recent click events")
    private List<RecentClickResponse> recentClicks;
}