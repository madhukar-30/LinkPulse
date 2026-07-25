package com.linkpulse.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateLinkRequest {

    @NotBlank(message = "Original URL is required")
    @URL(message = "Original URL must be a valid URL")
    @Size(max = 2048, message = "Original URL must not exceed 2048 characters")
    private String originalUrl;
}
