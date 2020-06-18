package com.lov.lovwebapp.repo;

import com.lov.lovwebapp.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Long> {
    List<Activity> findAllByActivityGoal_User_Id(long id);
    List<Activity> findAllByActivityGoal_GoalName(String goalName);
}
