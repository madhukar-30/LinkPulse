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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private static final String ALPHANUMERIC_CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_CODE_LENGTH = 8;
    private static final int MAX_SHORT_CODE_GENERATION_ATTEMPTS = 20;
    private static final int QR_CODE_SIZE = 300;
    private static final Pattern CUSTOM_ALIAS_PATTERN =
            Pattern.compile("^[A-Za-z0-9_-]{3,50}$");

    private final LinkRepository linkRepository;
    private final ClickEventRepository clickEventRepository;
    private final UserAgentParser userAgentParser;
    private final SecureRandom secureRandom;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    @Transactional
    public LinkResponse createLink(CreateLinkRequest request) {
        User currentUser = getCurrentUser();

        Link link = Link.builder()
                .originalUrl(request.getOriginalUrl())
                .shortCode(resolveShortCodeForCreate(request.getCustomAlias()))
                .expiresAt(validateExpirationDate(request.getExpiresAt()))
                .user(currentUser)
                .build();

        return toResponse(linkRepository.save(link));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkResponse> getUserLinks() {
        User currentUser = getCurrentUser();

        return linkRepository.findAllByUserOrderByCreatedAtDesc(currentUser).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public LinkResponse updateLink(Long id, UpdateLinkRequest request) {
        Link link = getUserLink(id, getCurrentUser());
        link.setOriginalUrl(request.getOriginalUrl());
        link.setShortCode(resolveShortCodeForUpdate(link.getShortCode(), request.getCustomAlias()));
        if (request.getExpiresAt() != null) {
            link.setExpiresAt(validateExpirationDate(request.getExpiresAt()));
        }

        return toResponse(linkRepository.save(link));
    }

    @Override
    @Transactional
    public void deleteLink(Long id) {
        Link link = getUserLink(id, getCurrentUser());
        linkRepository.delete(link);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generateQrCode(Long id) {
        Link link = getUserLink(id, getCurrentUser());

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    buildPublicShortUrl(link.getShortCode()),
                    BarcodeFormat.QR_CODE,
                    QR_CODE_SIZE,
                    QR_CODE_SIZE
            );
            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "PNG", outputStream);

            return outputStream.toByteArray();
        } catch (WriterException | IOException exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to generate QR code",
                    exception
            );
        }
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

        if (isExpired(link)) {
            throw new ResponseStatusException(HttpStatus.GONE, "Short link has expired");
        }

        link.setClickCount(link.getClickCount() + 1);
        UserAgentDetails userAgentDetails = userAgentParser.parse(request);

        clickEventRepository.save(ClickEvent.builder()
                .link(link)
                .ipAddress(extractClientIpAddress(request))
                .userAgent(request.getHeader("User-Agent"))
                .browser(userAgentDetails.browser())
                .operatingSystem(userAgentDetails.operatingSystem())
                .deviceType(userAgentDetails.deviceType())
                .referrer(request.getHeader("Referer"))
                .build());

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found"));
    }

    private String resolveShortCodeForCreate(String customAlias) {
        String normalizedAlias = normalizeAlias(customAlias);

        if (normalizedAlias == null) {
            return generateUniqueShortCode();
        }

        validateCustomAlias(normalizedAlias);

        if (linkRepository.existsByShortCode(normalizedAlias)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Custom alias is already in use");
        }

        return normalizedAlias;
    }

    private String resolveShortCodeForUpdate(String currentShortCode, String customAlias) {
        String normalizedAlias = normalizeAlias(customAlias);

        if (normalizedAlias == null || normalizedAlias.equals(currentShortCode)) {
            return currentShortCode;
        }

        validateCustomAlias(normalizedAlias);

        if (linkRepository.existsByShortCode(normalizedAlias)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Custom alias is already in use");
        }

        return normalizedAlias;
    }

    private String normalizeAlias(String customAlias) {
        if (customAlias == null) {
            return null;
        }

        String normalizedAlias = customAlias.trim();
        return normalizedAlias.isEmpty() ? null : normalizedAlias;
    }

    private void validateCustomAlias(String customAlias) {
        if (!CUSTOM_ALIAS_PATTERN.matcher(customAlias).matches()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Custom alias must be 3 to 50 characters and contain only letters, numbers, hyphens, or underscores"
            );
        }
    }

    private LocalDateTime validateExpirationDate(LocalDateTime expiresAt) {
        if (expiresAt != null && expiresAt.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Expiration date must not be in the past"
            );
        }

        return expiresAt;
    }

    private boolean isExpired(Link link) {
        return link.getExpiresAt() != null && !link.getExpiresAt().isAfter(LocalDateTime.now());
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
        return realIp != null && !realIp.isBlank() ? realIp : request.getRemoteAddr();
    }

    private String buildPublicShortUrl(String shortCode) {
        return baseUrl.endsWith("/") ? baseUrl + shortCode : baseUrl + "/" + shortCode;
    }

    private LinkResponse toResponse(Link link) {
        return LinkResponse.builder()
                .id(link.getId())
                .originalUrl(link.getOriginalUrl())
                .shortCode(link.getShortCode())
                .clickCount(link.getClickCount())
                .createdAt(link.getCreatedAt())
                .expiresAt(link.getExpiresAt())
                .build();
    }
}
