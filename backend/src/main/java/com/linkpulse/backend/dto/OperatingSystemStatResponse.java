package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Operating system statistics for a shortened link.")
public class OperatingSystemStatResponse {

    @Schema(
            description = "Name of the detected operating system.",
            example = "Windows"
    )
    private final String operatingSystem;

    @Schema(
            description = "Total number of clicks recorded from this operating system.",
            example = "20"
    )
    private final Long clicks;
}