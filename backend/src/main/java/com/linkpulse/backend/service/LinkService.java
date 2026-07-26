package com.linkpulse.backend.service;

import com.linkpulse.backend.dto.CreateLinkRequest;
import com.linkpulse.backend.dto.LinkResponse;
import com.linkpulse.backend.dto.UpdateLinkRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;


public interface LinkService {

    LinkResponse createLink(CreateLinkRequest request);

    Page<LinkResponse> getUserLinks(int page, int size, String search, String sort);

    LinkResponse updateLink(Long id, UpdateLinkRequest request);

    void deleteLink(Long id);

    byte[] generateQrCode(Long id);

    String resolveOriginalUrl(String shortCode, HttpServletRequest request);
}
