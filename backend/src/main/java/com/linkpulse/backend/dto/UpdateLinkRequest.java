package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request payload for updating an existing shortened link.")
public class UpdateLinkRequest {

    @Schema(
            description = "The updated destination URL.",
            example = "https://github.com"
    )
    @NotBlank(message = "Original URL is required")
    @URL(message = "Original URL must be a valid URL")
    @Size(max = 2048, message = "Original URL must not exceed 2048 characters")
    private String originalUrl;

    @Schema(
            description = "Optional custom alias. If omitted or blank, the current alias remains unchanged.",
            example = "portfolio"
    )
    @Pattern(
            regexp = "^$|^[A-Za-z0-9_-]{3,50}$",
            message = "Custom alias must be 3 to 50 characters and contain only letters, numbers, hyphens, or underscores"
    )
    private String customAlias;
}