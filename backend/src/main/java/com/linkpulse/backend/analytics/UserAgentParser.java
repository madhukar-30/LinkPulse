package com.linkpulse.backend.analytics;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UserAgentParser {

    private final Parser parser;

    public UserAgentDetails parse(HttpServletRequest request) {

        String userAgent = request.getHeader("User-Agent");

        if (userAgent == null || userAgent.isBlank()) {
            return new UserAgentDetails(
                    "Unknown",
                    "Unknown",
                    "Unknown"
            );
        }

        Client client = parser.parse(userAgent);

        String browser =
                client != null && client.userAgent != null
                        ? valueOrUnknown(client.userAgent.family)
                        : "Unknown";

        String operatingSystem =
                client != null && client.os != null
                        ? valueOrUnknown(client.os.family)
                        : "Unknown";

        return new UserAgentDetails(
                browser,
                operatingSystem,
                classifyDeviceType(userAgent, client)
        );
    }

    private String classifyDeviceType(String userAgent, Client client) {

        String normalizedUserAgent = userAgent.toLowerCase(Locale.ROOT);

        if (normalizedUserAgent.contains("bot")
                || normalizedUserAgent.contains("crawler")
                || normalizedUserAgent.contains("spider")) {
            return "Bot";
        }

        String deviceFamily =
                client != null && client.device != null
                        ? valueOrUnknown(client.device.family).toLowerCase(Locale.ROOT)
                        : "unknown";

        if (normalizedUserAgent.contains("ipad")
                || normalizedUserAgent.contains("tablet")
                || normalizedUserAgent.contains("kindle")
                || deviceFamily.contains("ipad")
                || deviceFamily.contains("tablet")) {
            return "Tablet";
        }

        if (normalizedUserAgent.contains("mobi")
                || normalizedUserAgent.contains("iphone")
                || normalizedUserAgent.contains("android")
                || deviceFamily.contains("iphone")
                || deviceFamily.contains("android")) {
            return "Mobile";
        }

        if (!"unknown".equals(deviceFamily)
                && !"other".equals(deviceFamily)) {
            return "Desktop";
        }

        if (normalizedUserAgent.contains("windows")
                || normalizedUserAgent.contains("macintosh")
                || normalizedUserAgent.contains("linux")) {
            return "Desktop";
        }

        return "Unknown";
    }

    private String valueOrUnknown(String value) {
        return value == null || value.isBlank()
                ? "Unknown"
                : value;
    }
}