package com.linkpulse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkResponse {

    private Long id;
    private String originalUrl;
    private String shortCode;
    private Long clickCount;
    private LocalDateTime createdAt;
}
