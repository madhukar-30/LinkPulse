package com.linkpulse.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Request to create a shortened link")
public class CreateLinkRequest {

    @Schema(description = "Original destination URL", example = "https://example.com/articles/linkpulse")
    @NotBlank(message = "Original URL is required")
    @URL(message = "Original URL must be a valid URL")
    @Size(max = 2048, message = "Original URL must not exceed 2048 characters")
    private String originalUrl;
}