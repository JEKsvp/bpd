package com.jeksvp.goalkeeper.repository;

import com.jeksvp.goalkeeper.domain.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
}
