package com.linkpulse.backend.controller;

import com.linkpulse.backend.dto.CreateLinkRequest;
import com.linkpulse.backend.dto.LinkResponse;
import com.linkpulse.backend.dto.UpdateLinkRequest;
import com.linkpulse.backend.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
            description = "Creates a new shortened link for the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Shortened link created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or URL"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<LinkResponse> createLink(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the URL to shorten.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateLinkRequest.class))
            )
            @RequestBody CreateLinkRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(linkService.createLink(request));
    }

    @GetMapping
    @Operation(
            summary = "Retrieve authenticated user's links",
            description = "Returns all shortened links owned by the authenticated user."
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
            summary = "Update a shortened URL",
            description = "Updates an existing shortened link owned by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Link updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or URL"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Link not found")
    })
    public ResponseEntity<LinkResponse> updateLink(
            @Parameter(
                    description = "Unique identifier of the link.",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated details for the shortened link.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateLinkRequest.class))
            )
            @RequestBody UpdateLinkRequest request
    ) {
        return ResponseEntity.ok(linkService.updateLink(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a shortened URL",
            description = "Deletes a shortened link owned by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Link deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Link not found")
    })
    public ResponseEntity<Void> deleteLink(
            @Parameter(
                    description = "Unique identifier of the link.",
                    example = "1"
            )
            @PathVariable Long id
    ) {
        linkService.deleteLink(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(
            summary = "Generate a QR code for a shortened URL",
            description = "Generates a PNG QR code containing the public shortened URL for a link owned by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "QR code generated successfully",
                    content = @Content(mediaType = MediaType.IMAGE_PNG_VALUE)
            ),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Link not found")
    })
    public ResponseEntity<byte[]> generateQrCode(
            @Parameter(
                    description = "Unique identifier of the link.",
                    example = "1"
            )
            @PathVariable Long id
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(linkService.generateQrCode(id));
    }
}
