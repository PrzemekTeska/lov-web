package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.*;
import com.lov.lovwebapp.repo.ActivityRepo;
import com.lov.lovwebapp.repo.GoalRepo;
import com.lov.lovwebapp.service.ActivityService;
import com.lov.lovwebapp.service.PenaltyService;
import com.lov.lovwebapp.service.RewardService;
import com.lov.lovwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ActivityServiceImplementation implements ActivityService {

    private ActivityRepo activityRepo;
    private UserService userService;
    private GoalRepo goalRepo;
    private PenaltyService penaltyService;
    private RewardService rewardService;

    @Autowired
    public ActivityServiceImplementation(ActivityRepo activityRepo, UserService userService,GoalRepo goalRepo,
                                         PenaltyService penaltyService,RewardService rewardService) {
        this.activityRepo = activityRepo;
        this.userService = userService;
        this.goalRepo=goalRepo;
        this.penaltyService=penaltyService;
        this.rewardService=rewardService;
    }

    @Override
    public List<Activity> getAllActivities(long userId) {
        return activityRepo.findAllByActivityGoal_User_Id(userId);
    }

    @Override
    public Activity getActivity(long id) {
        Optional<Activity> activity = activityRepo.findById(id);
        return activity.orElse(null);
    }

    @Override
    public void addActivity(Activity activity) {
        Goal activityGoal = activity.getActivityGoal();
        activity.setStartDate(activityGoal.getGoalStartDate());
        switch (activity.getFrequency()){
            case "daily":
                activity.setCounter((int) DAYS.between(activityGoal.getGoalStartDate(), activityGoal.getGoalEndDate()));
                activity.setEndDateTime(LocalDateTime.now().plusDays((DAYS.between(LocalDate.now(),activityGoal.getGoalStartDate()))+1));
                activity.setCounterString(getBeginningOfCounter(activity) + "/" + DAYS.between(activity.getStartDate(),
                        activity.getActivityGoal().getGoalEndDate()));
                break;
            case "weekly":
                activity.setCounter((int) DAYS.between(activityGoal.getGoalStartDate(), activityGoal.getGoalEndDate())/7);
                activity.setEndDateTime(LocalDateTime.now().plusDays((DAYS.between(LocalDate.now(),activityGoal.getGoalStartDate()))+7));
                activity.setCounterString(getBeginningOfCounterWeekly(activity) + "/"
                        + (DAYS.between(activity.getStartDate(), activity.getActivityGoal().getGoalEndDate())/7));
                break;
            case "single":
                activity.setCounter(0);
                activity.setCounterString("1/1");
        }
        activityRepo.save(activity);
    }

    @Override
    public boolean deleteActivity(long id) {
        if (activityRepo.findById(id).isPresent()) {
            activityRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void deleteFailedActivity(long id) {
        Activity activity = activityRepo.findById(id).get();
        addDuplicateActivity(activity);
        changeStringCounter(activity);
        GoalSucceededAndFailedActivityCounter(activity,false);
        List<Penalty> penaltyList =penaltyService.getPenaltiesByGoalNameAndUserName(activity.getActivityGoal().getGoalName(),activity.getActivityGoal().getUser().getId());
        if(!penaltyList.isEmpty()) penaltyService.setFailedInARow(penaltyList,true);
        User user = userService.getUserByID(activity.getActivityGoal().getUser().getId());
        int points = user.getPoints();
        points = points - activity.getActivityPoints();
        if (points < 0) points = 0;
        user.setPoints(points);
        userService.saveUser(user);
        if(activity.getCounter() <= 0){
            activityRepo.deleteById(id);
        }
    }

    @Override
    public void deleteCompletedActivity(long id) {
        Activity activity = activityRepo.findById(id).get();
        addDuplicateActivity(activity);
        GoalSucceededAndFailedActivityCounter(activity,true);

        List<Penalty> penaltyList =penaltyService.getPenaltiesByGoalNameAndUserName(activity.getActivityGoal().getGoalName(),activity.getActivityGoal().getUser().getId());
        if(!penaltyList.isEmpty()) penaltyService.setFailedInARow(penaltyList,false);

        List<Reward> rewardList =rewardService.getRewardsByGoalNameAndUserName(activity.getActivityGoal().getGoalName(),activity.getActivityGoal().getUser().getId());
        int maxActivityPoint = getMaxActivityPoints(activity.getActivityGoal().getGoalName());
        int succeededActivityPoints = getSucceededActivityPoints(activity.getActivityGoal().getGoalName());
        if(!rewardList.isEmpty()) rewardService.setPercentage(rewardList, maxActivityPoint,succeededActivityPoints);
        rewardService.fullPercentageReward(maxActivityPoint,succeededActivityPoints);

        changeStringCounter(activity);

        User user = userService.getUserByID(activity.getActivityGoal().getUser().getId());
        int points = user.getPoints();
        user.setPoints(points + activity.getActivityPoints());
        userService.saveUser(user);
        if(activity.getCounter() <= 0) {
            activityRepo.deleteById(id);
        }
    }

    private void changeStringCounter(Activity activity) {
        String counter = activity.getCounterString();
        int firstNumber = Integer.parseInt(counter.substring(0,counter.indexOf("/")));
        firstNumber++;
        String newCounter = firstNumber + counter.substring(counter.indexOf("/"));
        activity.setCounterString(newCounter);
    }

    private int getMaxActivityPoints(String goalName) {
        int value=0;
        List<Activity> activityList = activityRepo.findAllByActivityGoal_GoalName(goalName);
        for(Activity activity:activityList){
            String counterString=activity.getCounterString();
            value = value + Integer.parseInt(counterString.substring(0,counterString.indexOf("/")));
        }
        return value;
    }

    private int getSucceededActivityPoints(String goalName) {
        int value=0;
        List<Activity> activityList = activityRepo.findAllByActivityGoal_GoalName(goalName);
        for(Activity activity:activityList){
            String counterString=activity.getCounterString();
            value = value + Integer.parseInt(counterString.substring(counterString.indexOf("/")+1));
        }
        return value;
    }

    private void GoalSucceededAndFailedActivityCounter(Activity activity, boolean succeedFailed) {
        Goal goal=activity.getActivityGoal();
        int counter=0;
        if(succeedFailed){
            counter= goal.getSucceededActivityCounter();
            goal.setSucceededActivityCounter(++counter);
        }else{
            counter=goal.getFailedActivityCounter();
            goal.setFailedActivityCounter(++counter);
        }
        goalRepo.save(goal);
    }

    private void setStringCounter(Activity activity){
        if(activity.getFrequency().equals("daily")) {
            activity.setCounterString(getBeginningOfCounter(activity) + "/" + DAYS.between(activity.getStartDate(),
                    activity.getActivityGoal().getGoalEndDate()));
        }
        else if(activity.getFrequency().equals("weekly")) {
            activity.setCounterString(getBeginningOfCounterWeekly(activity) + "/"
                    + (DAYS.between(activity.getStartDate(), activity.getActivityGoal().getGoalEndDate())/7));
        }
    }


    @Override
    public boolean updateActivity(Activity activity) {
        activity.setStartDate(LocalDate.now());
        setStringCounter(activity);
        addActivity(activity);
        return activityRepo.findById(activity.getId()).get().equals(activity);
    }


    public void addDuplicateActivity(Activity activity) {
        int counter = activity.getCounter();
        if (counter > 0) {
            activity.setCounter(counter - 1);
            activity.setEndDateTime(activity.getEndDateTime().plusDays(1));
            activityRepo.save(activity);
        }
    }

    @Override
    public void deleteExpiredActivity(Principal principal) {
        List<Activity> activityList = activityRepo.findAllByActivityGoal_User_Id(userService.getUserByName(principal.getName()).getId());
        for (Activity activity : activityList) {
            if (activity.getEndDateTime().isBefore(LocalDateTime.now())) {
                deleteFailedActivity(activity.getId());
            }
        }
    }

    private String getBeginningOfCounter(Activity activity) {
        long result = DAYS.between(activity.getStartDate(), activity.getActivityGoal().getGoalEndDate())-activity.getCounter();
        return String.valueOf(++result);
    }

    private String getBeginningOfCounterWeekly(Activity activity) {
        long result = (DAYS.between(activity.getStartDate(), activity.getActivityGoal().getGoalEndDate())/7)-activity.getCounter() ;
        return String.valueOf(++result);
    }
}
