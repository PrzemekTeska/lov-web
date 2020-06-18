package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.GoalForInfo;
import com.lov.lovwebapp.model.MailerInfo;
import com.lov.lovwebapp.model.Reward;
import com.lov.lovwebapp.repo.GoalForInfoRepo;
import com.lov.lovwebapp.repo.GoalRepo;
import com.lov.lovwebapp.repo.RewardRepo;
import com.lov.lovwebapp.service.MailInfoService;
import com.lov.lovwebapp.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RewardServiceImplementation implements RewardService {

    private RewardRepo rewardRepo;
    private GoalRepo goalRepo;
    private MailInfoService mailInfoService;
    private GoalForInfoRepo goalForInfoRepo;

    @Autowired
    public RewardServiceImplementation(RewardRepo rewardRepo,GoalRepo goalRepo,MailInfoService mailInfoService,GoalForInfoRepo goalForInfoRepo) {
        this.rewardRepo = rewardRepo;
        this.goalRepo=goalRepo;
        this.mailInfoService=mailInfoService;
        this.goalForInfoRepo=goalForInfoRepo;
    }

    @Override
    public List<Reward> getAllRewards(long userId) {
        List<Reward> rewardList = rewardRepo.findAllByGoal_User_Id(userId);
        return rewardList.stream().filter(e->e.getPercentage() < e.getPercentageLimit()).collect(Collectors.toList());
    }

    @Override
    public List<Reward> getAllActiveRewards(long userId) {
        List<Reward> rewardList = rewardRepo.findAllByGoal_User_Id(userId);
        return rewardList.stream().filter(e->e.getPercentage()>=e.getPercentageLimit()).collect(Collectors.toList());
    }

    @Override
    public List<Reward> getRewardsByGoalNameAndUserName(String goalName,long userId) {
        return rewardRepo.findAllByGoal_GoalNameAndGoal_User_Id(goalName,userId);
    }

    @Override
    public Reward getReward(Long id) {
        Optional<Reward> reward = rewardRepo.findById(id);
        return reward.orElse(null);
    }

    @Override
    public void addReward(Reward reward) {
        reward.setPercentage(0);
        rewardRepo.save(reward);
    }

    @Override
    public void setPercentage(List<Reward> rewardList,int succeededActivities, int allActivities) {
        for(Reward reward:rewardList){
            int result=(int)(((double)succeededActivities / (double) allActivities) * 100);
            MailerInfo mailerInfo = mailInfoService.getMailerInfoByUser(reward.getGoal().getUser());
            List<GoalForInfo> goalForInfoList = mailerInfo.getGoalForInfo();
            GoalForInfo goalForInfo = goalForInfoList.stream().filter(e->e.getGoalName().equals(reward.getGoal().getGoalName())).findFirst().get();
            goalForInfo.setPercentage(result);
            goalForInfoRepo.save(goalForInfo);

            reward.setPercentage(result);
            if(reward.getPercentage()>reward.getPercentageLimit()){
                reward.setGoal(goalRepo.findById(1L).get());

            }
        }
        rewardRepo.saveAll(rewardList);
    }

    public void fullPercentageReward(int succeededActivities, int allActivities){
        if((int)(((double)succeededActivities / (double) allActivities) * 100)==100) {
            String[] rewards = {"Cheat meal", "Day off", "Beer", "Go out", "Play video games", "Read favourite book", "Buy something", "Enjoy sun", "Nap time", "Netflix"};
            Random random = new Random();
            Reward reward = new Reward(rewards[random.nextInt(10)], 100,100, goalRepo.findById(1L).get());
            rewardRepo.save(reward);
        }
    }

    @Override
    public boolean deleteReward(Long id) {
        if (rewardRepo.findById(id).isPresent()) {
            rewardRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReward(Reward reward) {
        addReward(reward);
        return rewardRepo.findById(reward.getId()).get().equals(reward);
    }

}
