package com.linkpulse.backend.service;

import com.linkpulse.backend.dto.AnalyticsResponse;
import com.linkpulse.backend.dto.RecentClickResponse;
import com.linkpulse.backend.entity.ClickEvent;
import com.linkpulse.backend.entity.Link;
import com.linkpulse.backend.entity.User;
import com.linkpulse.backend.repository.ClickEventRepository;
import com.linkpulse.backend.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final LinkRepository linkRepository;
    private final ClickEventRepository clickEventRepository;

    @Override
    @Transactional(readOnly = true)
    public AnalyticsResponse getLinkAnalytics(Long linkId, User authenticatedUser) {
        if (authenticatedUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }

        Link link = linkRepository.findByIdAndUser(linkId, authenticatedUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Link not found"));

        return AnalyticsResponse.builder()
                .id(link.getId())
                .originalUrl(link.getOriginalUrl())
                .shortCode(link.getShortCode())
                .createdAt(link.getCreatedAt())
                .totalClicks(link.getClickCount())
                .dailyClicks(clickEventRepository.findDailyClickStatsByLink(link))
                .browserStats(clickEventRepository.findBrowserStatsByLink(link))
                .operatingSystemStats(clickEventRepository.findOperatingSystemStatsByLink(link))
                .recentClicks(clickEventRepository.findTop10ByLinkOrderByClickedAtDesc(link).stream()
                        .map(this::toRecentClickResponse)
                        .toList())
                .build();
    }

    private RecentClickResponse toRecentClickResponse(ClickEvent clickEvent) {
        return RecentClickResponse.builder()
                .clickedAt(clickEvent.getClickedAt())
                .ipAddress(clickEvent.getIpAddress())
                .userAgent(clickEvent.getUserAgent())
                .browser(clickEvent.getBrowser())
                .operatingSystem(clickEvent.getOperatingSystem())
                .deviceType(clickEvent.getDeviceType())
                .referrer(clickEvent.getReferrer())
                .build();
    }
}
