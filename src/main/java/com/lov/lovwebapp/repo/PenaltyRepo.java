package com.lov.lovwebapp.repo;

import com.lov.lovwebapp.model.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenaltyRepo extends JpaRepository<Penalty, Long> {
    List<Penalty> findAllByGoal_GoalNameAndGoal_User_Id(String goalName, long userId);
    List<Penalty> findAllByGoal_User_Id(long id);
}
