package com.linkpulse.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "click_events",
        indexes = @Index(name = "idx_click_events_link_clicked_at", columnList = "link_id, clicked_at")
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClickEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "link_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Link link;

    @Column(name = "clicked_at", nullable = false, updatable = false)
    private LocalDateTime clickedAt;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 2048)
    private String userAgent;

    @Column(nullable = false, length = 128)
    private String browser;

    @Column(name = "operating_system", nullable = false, length = 128)
    private String operatingSystem;

    @Column(name = "device_type", nullable = false, length = 32)
    private String deviceType;

    @Column(length = 2048)
    private String referrer;

    @PrePersist
    private void initializeClickedAt() {
        if (clickedAt == null) {
            clickedAt = LocalDateTime.now();
        }
    }
}
