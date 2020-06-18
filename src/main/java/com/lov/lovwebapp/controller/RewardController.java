package com.lov.lovwebapp.controller;

import com.lov.lovwebapp.model.Activity;
import com.lov.lovwebapp.model.Goal;
import com.lov.lovwebapp.model.Reward;
import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.service.ActivityService;
import com.lov.lovwebapp.service.GoalService;
import com.lov.lovwebapp.service.RewardService;
import com.lov.lovwebapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class RewardController {

    private RewardService rewardService;
    private UserService userService;
    private GoalService goalService;
    private ActivityService activityService;

    public RewardController(RewardService rewardService, UserService userService, GoalService goalService,ActivityService activityService) {
        this.rewardService = rewardService;
        this.userService = userService;
        this.goalService = goalService;
        this.activityService=activityService;
    }

    @RequestMapping("/rewards")
    public String rewards(Model model, Principal principal) {
        long id =userService.getUserByName(principal.getName()).getId();
        model.addAttribute("rewardList", rewardService.getAllRewards(id));
        List<Reward> activeRewardList=rewardService.getAllActiveRewards(id);
        model.addAttribute("activeRewardList",activeRewardList);
        model.addAttribute("checkActiveRewards", !activeRewardList.isEmpty());
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "rewards";
    }

    @RequestMapping("/redirectToAddReward")
    public ModelAndView redirectToAddReward(Principal principal) {
        List<Goal> goalList = goalService.getAllGoals(userService.getUserByName(principal.getName()).getId());
        List<Activity> activityList = activityService.getAllActivities(userService.getUserByName(principal.getName()).getId());
        if (!goalList.isEmpty()) {
            if(!activityList.isEmpty()) return new ModelAndView("redirect:/addreward");
            else return new ModelAndView("redirect:/addactivityfail?warning=a_reward&endpoint=reward");
        }
        return new ModelAndView("redirect:/addgoalnoactivity?warning=a_reward&endpoint=reward");
    }

    @RequestMapping("/addreward")
    public ModelAndView addReward(Model model, Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        //model.addAttribute("goalId", 0);
        return new ModelAndView("addreward", "reward", new Reward());
    }

    @RequestMapping("/savereward")
    public ModelAndView saveReward(Reward reward) {
        rewardService.addReward(reward);
        return new ModelAndView("redirect:/rewards");
    }

    @RequestMapping("/redirectToRewards")
    public ModelAndView redirectToRewards() {
        return new ModelAndView("redirect:/rewards");
    }

    @RequestMapping(value = "/rewards/delete/{id}", method = RequestMethod.GET)
    public String deleteReward(@PathVariable Long id) {
        rewardService.deleteReward(id);
        return "redirect:/rewards";
    }

    @RequestMapping(value = "rewards/editreward/{id}", method = RequestMethod.POST)
    public String updateReward(@PathVariable Long id, Reward reward) {
        rewardService.updateReward(reward);
        return "redirect:/rewards";
    }

    @RequestMapping("/editreward/{id}")
    public String editReward(@PathVariable Long id, Model model,Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        model.addAttribute("reward", rewardService.getReward(id));
        return "editreward";
    }
}
