package com.linkpulse.backend.repository;

import com.linkpulse.backend.dto.BrowserStatResponse;
import com.linkpulse.backend.dto.DailyClickResponse;
import com.linkpulse.backend.dto.OperatingSystemStatResponse;
import com.linkpulse.backend.entity.ClickEvent;
import com.linkpulse.backend.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {

    List<ClickEvent> findByLink(Link link);

    List<ClickEvent> findTop10ByLinkOrderByClickedAtDesc(Link link);

    @Query("""
            select new com.linkpulse.backend.dto.BrowserStatResponse(ce.browser, count(ce))
            from ClickEvent ce
            where ce.link = :link
            group by ce.browser
            order by count(ce) desc, ce.browser asc
            """)
    List<BrowserStatResponse> findBrowserStatsByLink(@Param("link") Link link);

    @Query("""
            select new com.linkpulse.backend.dto.OperatingSystemStatResponse(ce.operatingSystem, count(ce))
            from ClickEvent ce
            where ce.link = :link
            group by ce.operatingSystem
            order by count(ce) desc, ce.operatingSystem asc
            """)
    List<OperatingSystemStatResponse> findOperatingSystemStatsByLink(@Param("link") Link link);

    @Query("""
            select new com.linkpulse.backend.dto.DailyClickResponse(
                cast(ce.clickedAt as LocalDate), count(ce)
            )
            from ClickEvent ce
            where ce.link = :link
            group by cast(ce.clickedAt as LocalDate)
            order by cast(ce.clickedAt as LocalDate) asc
            """)
    List<DailyClickResponse> findDailyClickStatsByLink(@Param("link") Link link);
}
