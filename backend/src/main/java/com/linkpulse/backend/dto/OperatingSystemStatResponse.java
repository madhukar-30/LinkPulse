package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Operating system click statistic")
public class OperatingSystemStatResponse {

    @Schema(
            description = "Detected operating system",
            example = "Windows"
    )
    private final String operatingSystem;

    @Schema(
            description = "Number of clicks from the operating system",
            example = "20"
    )
    private final Long clicks;
}