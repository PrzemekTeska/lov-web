package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.Goal;
import com.lov.lovwebapp.model.User;

import java.util.List;

public interface GoalService {
    List<Goal> getAllGoals(long userId);
    Goal getGoal(Long id);
    boolean checkGoalExpiration(User user);
    void addGoal(Goal goal);
    boolean deleteGoal(Long id);
    boolean updateGoal(Goal goal);
}
