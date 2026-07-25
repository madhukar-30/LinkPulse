package com.linkpulse.backend.controller;

import com.linkpulse.backend.dto.CreateLinkRequest;
import com.linkpulse.backend.dto.LinkResponse;
import com.linkpulse.backend.dto.UpdateLinkRequest;
import com.linkpulse.backend.service.LinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping
    public ResponseEntity<LinkResponse> createLink(@Valid @RequestBody CreateLinkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(linkService.createLink(request));
    }

    @GetMapping
    public ResponseEntity<List<LinkResponse>> getUserLinks() {
        return ResponseEntity.ok(linkService.getUserLinks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LinkResponse> updateLink(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLinkRequest request) {
        return ResponseEntity.ok(linkService.updateLink(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.noContent().build();
    }
}
