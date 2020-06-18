package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.Penalty;
import com.lov.lovwebapp.repo.GoalRepo;
import com.lov.lovwebapp.repo.PenaltyRepo;
import com.lov.lovwebapp.service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PenaltyServiceImplementation implements PenaltyService {

    private PenaltyRepo penaltyRepo;
    private GoalRepo goalRepo;

    @Autowired
    public PenaltyServiceImplementation(PenaltyRepo penaltyRepo,GoalRepo goalRepo) {
        this.penaltyRepo = penaltyRepo;
        this.goalRepo = goalRepo;
    }

    @Override
    public List<Penalty> getAllPenalties(long userId) {
        List<Penalty> penaltyList = penaltyRepo.findAllByGoal_User_Id(userId);
        return penaltyList.stream().filter(e->e.getFailedInARow() < e.getFailedInARowLimit()).collect(Collectors.toList());
    }

    @Override
    public List<Penalty> getAllActivePenalties(long userId) {
        List<Penalty> penaltyList = penaltyRepo.findAllByGoal_User_Id(userId);
        return penaltyList.stream().filter(e->e.getFailedInARow() >= e.getFailedInARowLimit()).collect(Collectors.toList());
    }


    @Override
    public List<Penalty> getPenaltiesByGoalNameAndUserName(String goalName,long userId) {
        return penaltyRepo.findAllByGoal_GoalNameAndGoal_User_Id(goalName, userId);
    }

    @Override
    public Penalty getPenalty(Long id) {
        Optional<Penalty> penalty = penaltyRepo.findById(id);
        return penalty.orElse(null);
    }

    @Override
    public void addNewPenalty(Penalty penalty) {
        penalty.setFailedInARow(0);
        penaltyRepo.save(penalty);
    }

    @Override
    public void addPenalty(Penalty penalty) {
        penaltyRepo.save(penalty);
    }


    @Override
    public void setFailedInARow(List<Penalty> penaltyList, boolean done) {
        for(Penalty penalty:penaltyList) {
            int value = penalty.getFailedInARow();
            if(done) {
                penalty.setFailedInARow(++value);

                if(value>=penalty.getFailedInARowLimit()) {
                    penalty.setGoal(goalRepo.findById(1L).get());
                }
            }
            else if(!done){
                if (value >= 0) penalty.setFailedInARow(0);
            }
        }
        penaltyRepo.saveAll(penaltyList);
    }

    @Override
    public boolean deletePenalty(Long id) {
        if (penaltyRepo.findById(id).isPresent()) {
            penaltyRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePenalty(Penalty penalty) {
        addPenalty(penalty);
        return penaltyRepo.findById(penalty.getId()).get().equals(penalty);
    }

}
