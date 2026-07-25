package com.linkpulse.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Tag(
        name = "Testing",
        description = "Protected application test endpoint."
)
public class TestController {

    @GetMapping
    @Operation(
            summary = "Verify JWT authentication",
            description = "Returns a success message for an authenticated request."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "JWT authentication verified"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<String> hello() {

        return ResponseEntity.ok("JWT Authentication Successful!");
    }
}