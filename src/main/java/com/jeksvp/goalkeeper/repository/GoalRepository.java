package com.jeksvp.goalkeeper.repository;

import com.jeksvp.goalkeeper.domain.entity.Goal;
import com.jeksvp.goalkeeper.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GoalRepository extends MongoRepository<Goal, Long> {

    List<Goal> findByUsername(String username);

    Goal findByProgressesContains(Long progressId);
}
