package com.linkpulse.backend.controller;

import com.linkpulse.backend.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Redirect",
        description = "Public URL redirection endpoints."
)
public class RedirectController {

    private final LinkService linkService;

    @GetMapping("/{shortCode}")
    @Operation(
            summary = "Redirect to the original URL",
            description = "Records the click event and redirects the visitor to the original destination URL."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Redirected to the original URL"),
            @ApiResponse(responseCode = "404", description = "Short link not found")
    })
    public ResponseEntity<Void> redirectToOriginalUrl(
            @Parameter(description = "Generated short code used for public redirection")
            @PathVariable String shortCode,
            HttpServletRequest request
    ) {
        String originalUrl = linkService.resolveOriginalUrl(shortCode, request);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}