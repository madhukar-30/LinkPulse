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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
@Tag(
        name = "Analytics",
        description = "Analytics and statistics for shortened links."
)
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/{id}/analytics")
    @Operation(
            summary = "Retrieve analytics for a shortened URL",
            description = "Returns click totals, daily activity, browser and operating system statistics, and recent click events."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Analytics retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Link not found")
    })
    public ResponseEntity<AnalyticsResponse> getLinkAnalytics(
            @Parameter(
                    description = "Unique identifier of the shortened link",
                    required = true
            )
            @PathVariable Long id,
            @AuthenticationPrincipal User authenticatedUser) {

        AnalyticsResponse response =
                analyticsService.getLinkAnalytics(id, authenticatedUser);

        return ResponseEntity.ok(response);
    }
}