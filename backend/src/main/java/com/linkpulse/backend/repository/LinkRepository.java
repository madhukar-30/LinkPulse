package com.linkpulse.backend.repository;

import com.linkpulse.backend.entity.Link;
import com.linkpulse.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    List<Link> findAllByUserOrderByCreatedAtDesc(User user);

    Optional<Link> findByIdAndUser(Long id, User user);
}
