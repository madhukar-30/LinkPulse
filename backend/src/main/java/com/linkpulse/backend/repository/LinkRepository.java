package com.linkpulse.backend.repository;

import com.linkpulse.backend.entity.Link;
import com.linkpulse.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    @Query("""
            select l from Link l
            where l.user = :user
              and (
                    :search = ''
                    or lower(l.originalUrl) like lower(concat('%', :search, '%'))
                    or lower(l.shortCode) like lower(concat('%', :search, '%'))
              )
            """)
    Page<Link> findByUserWithSearch(
            @Param("user") User user,
            @Param("search") String search,
            Pageable pageable
    );

    Optional<Link> findByIdAndUser(Long id, User user);
}