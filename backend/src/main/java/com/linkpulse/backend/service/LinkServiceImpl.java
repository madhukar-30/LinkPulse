package com.linkpulse.backend.service;

import com.linkpulse.backend.analytics.UserAgentDetails;
import com.linkpulse.backend.analytics.UserAgentParser;
import com.linkpulse.backend.dto.CreateLinkRequest;
import com.linkpulse.backend.dto.LinkResponse;
import com.linkpulse.backend.dto.UpdateLinkRequest;
import com.linkpulse.backend.entity.ClickEvent;
import com.linkpulse.backend.entity.Link;
import com.linkpulse.backend.entity.User;
import com.linkpulse.backend.repository.ClickEventRepository;
import com.linkpulse.backend.repository.LinkRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private static final String ALPHANUMERIC_CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_CODE_LENGTH = 8;
    private static final int MAX_SHORT_CODE_GENERATION_ATTEMPTS = 20;

    private final LinkRepository linkRepository;
    private final ClickEventRepository clickEventRepository;
    private final UserAgentParser userAgentParser;
    private final SecureRandom secureRandom;

    @Override
    @Transactional
    public LinkResponse createLink(CreateLinkRequest request) {
        User currentUser = getCurrentUser();

        Link link = Link.builder()
                .originalUrl(request.getOriginalUrl())
                .shortCode(generateUniqueShortCode())
                .user(currentUser)
                .build();

        return toResponse(linkRepository.save(link));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkResponse> getUserLinks() {
        User currentUser = getCurrentUser();

        return linkRepository.findAllByUserOrderByCreatedAtDesc(currentUser)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public LinkResponse updateLink(Long id, UpdateLinkRequest request) {
        Link link = getUserLink(id, getCurrentUser());

        link.setOriginalUrl(request.getOriginalUrl());

        return toResponse(link);
    }

    @Override
    @Transactional
    public void deleteLink(Long id) {
        Link link = getUserLink(id, getCurrentUser());
        linkRepository.delete(link);
    }

    @Override
    @Transactional
    public String resolveOriginalUrl(String shortCode, HttpServletRequest request) {
        Link link = linkRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Short link not found"
                        )
                );

        link.setClickCount(link.getClickCount() + 1);

        UserAgentDetails userAgentDetails = userAgentParser.parse(request);

        clickEventRepository.save(
                ClickEvent.builder()
                        .link(link)
                        .ipAddress(extractClientIpAddress(request))
                        .userAgent(request.getHeader("User-Agent"))
                        .browser(userAgentDetails.browser())
                        .operatingSystem(userAgentDetails.operatingSystem())
                        .deviceType(userAgentDetails.deviceType())
                        .referrer(request.getHeader("Referer"))
                        .build()
        );

        return link.getOriginalUrl();
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }

        return user;
    }

    private Link getUserLink(Long id, User user) {
        return linkRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found"));
    }

    private String generateUniqueShortCode() {
        for (int attempt = 0; attempt < MAX_SHORT_CODE_GENERATION_ATTEMPTS; attempt++) {
            String shortCode = generateShortCode();

            if (!linkRepository.existsByShortCode(shortCode)) {
                return shortCode;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Unable to generate a unique short code"
        );
    }

    private String generateShortCode() {
        StringBuilder shortCode = new StringBuilder(SHORT_CODE_LENGTH);

        for (int index = 0; index < SHORT_CODE_LENGTH; index++) {
            int characterIndex = secureRandom.nextInt(ALPHANUMERIC_CHARACTERS.length());
            shortCode.append(ALPHANUMERIC_CHARACTERS.charAt(characterIndex));
        }

        return shortCode.toString();
    }

    private String extractClientIpAddress(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");

        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",", 2)[0].trim();
        }

        String realIp = request.getHeader("X-Real-IP");
        return realIp != null && !realIp.isBlank()
                ? realIp
                : request.getRemoteAddr();
    }

    private LinkResponse toResponse(Link link) {
        return LinkResponse.builder()
                .id(link.getId())
                .originalUrl(link.getOriginalUrl())
                .shortCode(link.getShortCode())
                .clickCount(link.getClickCount())
                .createdAt(link.getCreatedAt())
                .build();
    }
}