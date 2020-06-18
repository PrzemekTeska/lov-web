package com.lov.lovwebapp.repo;

import com.lov.lovwebapp.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRepo extends JpaRepository<Reward, Long> {
    List<Reward> findAllByGoal_GoalNameAndGoal_User_Id(String goalName, long userId);
    List<Reward> findAllByGoal_User_Id(long id);
}
