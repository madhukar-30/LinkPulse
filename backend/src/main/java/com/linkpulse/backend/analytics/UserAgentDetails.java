package com.linkpulse.backend.analytics;

public record UserAgentDetails(
        String browser,
        String operatingSystem,
        String deviceType
) {
}
