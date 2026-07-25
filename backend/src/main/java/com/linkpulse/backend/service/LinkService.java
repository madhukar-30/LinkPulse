package com.linkpulse.backend.service;

import com.linkpulse.backend.dto.CreateLinkRequest;
import com.linkpulse.backend.dto.LinkResponse;
import com.linkpulse.backend.dto.UpdateLinkRequest;

import java.util.List;

public interface LinkService {

    LinkResponse createLink(CreateLinkRequest request);

    List<LinkResponse> getUserLinks();

    LinkResponse updateLink(Long id, UpdateLinkRequest request);

    void deleteLink(Long id);

    String resolveOriginalUrl(String shortCode);
}