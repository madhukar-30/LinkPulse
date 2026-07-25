package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Schema(description = "Click count for a calendar day")
public class DailyClickResponse {

    @Schema(
            description = "Date of recorded clicks",
            example = "2026-07-25"
    )
    private final LocalDate date;

    @Schema(
            description = "Number of clicks recorded on the date",
            example = "12"
    )
    private final Long clicks;
}