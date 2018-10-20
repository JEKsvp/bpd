package com.jeksvp.goalkeeper.repository;

import com.jeksvp.goalkeeper.entity.Goal;
import com.jeksvp.goalkeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal> findByUser(User user);
}
