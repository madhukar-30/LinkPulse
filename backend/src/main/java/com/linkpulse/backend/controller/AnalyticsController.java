package com.linkpulse.backend.controller;

import com.linkpulse.backend.dto.AnalyticsResponse;
import com.linkpulse.backend.entity.User;
import com.linkpulse.backend.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
@Tag(
        name = "Analytics",
        description = "Retrieve analytics and statistics for shortened links."
)
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/{id}/analytics")
    @Operation(
            summary = "Retrieve analytics for a shortened URL",
            description = "Returns detailed analytics for a shortened link, including total clicks, daily click activity, browser statistics, operating system statistics, and recent click events."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Analytics retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Link not found")
    })
    public ResponseEntity<AnalyticsResponse> getLinkAnalytics(

            @Parameter(
                    description = "Unique identifier of the link.",
                    example = "1"
            )
            @PathVariable Long id,

            @Parameter(hidden = true)
            @AuthenticationPrincipal User authenticatedUser) {

        return ResponseEntity.ok(
                analyticsService.getLinkAnalytics(id, authenticatedUser)
        );
    }
}