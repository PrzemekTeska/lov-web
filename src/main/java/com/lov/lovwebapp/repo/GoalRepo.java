package com.lov.lovwebapp.repo;

import com.lov.lovwebapp.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepo extends JpaRepository<Goal, Long> {
    List<Goal>findAllByUserId(long userId);
}
