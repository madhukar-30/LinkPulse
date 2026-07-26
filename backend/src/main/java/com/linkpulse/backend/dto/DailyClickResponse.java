package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Schema(description = "Daily click statistics for a shortened link.")
public class DailyClickResponse {

    @Schema(
            description = "Calendar date on which the clicks were recorded.",
            example = "2026-07-25"
    )
    private final LocalDate date;

    @Schema(
            description = "Total number of clicks recorded on this date.",
            example = "12"
    )
    private final Long clicks;
}