package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Browser click statistic")
public class BrowserStatResponse {

    @Schema(
            description = "Detected browser",
            example = "Chrome"
    )
    private final String browser;

    @Schema(
            description = "Number of clicks from the browser",
            example = "25"
    )
    private final Long clicks;
}