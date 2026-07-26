package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Browser statistics for a shortened link.")
public class BrowserStatResponse {

    @Schema(
            description = "Name of the detected web browser.",
            example = "Chrome"
    )
    private final String browser;

    @Schema(
            description = "Total number of clicks recorded from this browser.",
            example = "25"
    )
    private final Long clicks;
}