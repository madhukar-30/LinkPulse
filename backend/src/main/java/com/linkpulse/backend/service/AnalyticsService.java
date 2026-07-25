package com.linkpulse.backend.service;

import com.linkpulse.backend.dto.AnalyticsResponse;
import com.linkpulse.backend.entity.User;

public interface AnalyticsService {

    AnalyticsResponse getLinkAnalytics(Long linkId, User authenticatedUser);
}
