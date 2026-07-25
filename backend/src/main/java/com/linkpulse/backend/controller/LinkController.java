package com.linkpulse.backend.controller;

import com.linkpulse.backend.dto.CreateLinkRequest;
import com.linkpulse.backend.dto.LinkResponse;
import com.linkpulse.backend.dto.UpdateLinkRequest;
import com.linkpulse.backend.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
@Tag(
        name = "Links",
        description = "Create, update, delete and manage shortened links."
)
public class LinkController {

    private final LinkService linkService;

    @PostMapping
    @Operation(
            summary = "Create a shortened URL",
            description = "Creates a new shortened URL for the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Shortened link created"),
            @ApiResponse(responseCode = "400", description = "Invalid original URL"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<LinkResponse> createLink(@Valid @RequestBody CreateLinkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(linkService.createLink(request));
    }

    @GetMapping
    @Operation(
            summary = "Retrieve authenticated user's links",
            description = "Returns all shortened links belonging to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Links retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<LinkResponse>> getUserLinks() {
        return ResponseEntity.ok(linkService.getUserLinks());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing shortened URL",
            description = "Updates the destination URL of a link owned by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Link updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid original URL"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Link not found")
    })
    public ResponseEntity<LinkResponse> updateLink(
            @Parameter(description = "Unique identifier of the shortened link")
            @PathVariable Long id,
            @Valid @RequestBody UpdateLinkRequest request) {
        return ResponseEntity.ok(linkService.updateLink(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a shortened URL",
            description = "Deletes a link owned by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Link deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Link not found")
    })
    public ResponseEntity<Void> deleteLink(
            @Parameter(description = "Unique identifier of the shortened link")
            @PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.noContent().build();
    }
}