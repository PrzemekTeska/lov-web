package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.Penalty;

import java.util.List;

public interface PenaltyService {

    List<Penalty> getAllPenalties(long userId);

    List<Penalty> getAllActivePenalties(long goalId);

    List<Penalty> getPenaltiesByGoalNameAndUserName(String goalName, long userId);

    Penalty getPenalty(Long id);

    void addNewPenalty(Penalty penalty);

    void addPenalty(Penalty penalty);

    void setFailedInARow(List<Penalty> penalty, boolean done);

    boolean deletePenalty(Long id);

    boolean updatePenalty(Penalty penalty);

}
