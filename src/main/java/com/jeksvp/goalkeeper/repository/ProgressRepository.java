package com.jeksvp.goalkeeper.repository;

import com.jeksvp.goalkeeper.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
}
