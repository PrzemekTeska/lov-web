package com.lov.lovwebapp.controller;


import com.lov.lovwebapp.model.Activity;
import com.lov.lovwebapp.model.Goal;
import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.UserRepo;
import com.lov.lovwebapp.service.ActivityService;
import com.lov.lovwebapp.service.GoalService;
import com.lov.lovwebapp.service.MailInfoService;
import com.lov.lovwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
public class ActivityController {

    private ActivityService activityService;
    private UserService userService;
    private GoalService goalService;

    @Autowired
    public ActivityController(ActivityService activityService, UserService userService,
                              GoalService goalService, UserRepo userRepo,MailInfoService mailInfoService) {
        this.activityService = activityService;
        this.userService = userService;
        this.goalService = goalService;
    }

    @RequestMapping("/activities")
    public String activities(Model model, Principal principal) {
        model.addAttribute("activityList", activityService.getAllActivities(userService.getUserByName(principal.getName()).getId()));
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "activities";
    }

    @RequestMapping("/redirectToAddActivity")
    public ModelAndView redirectToAddActivity(Principal principal) {
        List<Goal>goalList = goalService.getAllGoals(userService.getUserByName(principal.getName()).getId());
        if(!goalList.isEmpty()) return new ModelAndView("redirect:/addactivity");
        return new ModelAndView("redirect:/addgoalnoactivity?warning=an_activity&endpoint=activity");
    }

    @RequestMapping("/addactivity")
    public ModelAndView addactivity(Model model, Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        model.addAttribute("goalId", 0);
        return new ModelAndView("addactivity", "activity", new Activity());
    }

    @RequestMapping("/saveactivity")
    public ModelAndView saveActivity(Activity activity,Model model, Principal principal) {
        if(activity.getFrequency().equals("weekly") && DAYS.between(LocalDate.now(),activity.getActivityGoal().getGoalEndDate())<7){
            model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
            model.addAttribute("goalId", 0);
            return new ModelAndView("addactivitynotaweek", "activity", new Activity());
        }
        activityService.addActivity(activity);
        return new ModelAndView("redirect:/activities");
    }

    @RequestMapping("/redirectToActivities")
    public ModelAndView redirectToActivities() {
        return new ModelAndView("redirect:/activities");
    }

    @RequestMapping(value = "/activities/delete/{id}", method = RequestMethod.GET)
    public String deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return "redirect:/activities";
    }

    @RequestMapping(value = "/activities/deleteCompleted/{id}", method = RequestMethod.GET)
    public String deleteCompletedActivity(@PathVariable Long id) {
        activityService.deleteCompletedActivity(id);
        return "redirect:/activities";
    }

    @RequestMapping(value = "/activities/deleteFailed/{id}", method = RequestMethod.GET)
    public String deleteFailedActivity(@PathVariable Long id) {
        activityService.deleteFailedActivity(id);
        return "redirect:/activities";
    }

    @RequestMapping(value = "activities/editactivity/{id}", method = RequestMethod.POST)
    public String updateActivity(@PathVariable Long id, Activity activity,Model model, Principal principal) {
        if(activity.getFrequency().equals("weekly") && DAYS.between(LocalDate.now(),activity.getActivityGoal().getGoalEndDate())<7){
            model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
            model.addAttribute("activity", activityService.getActivity(id));
            return "editactivityfail";
        }
        activityService.updateActivity(activity);
        return "redirect:/activities";
    }

    @RequestMapping("/editactivity/{id}")
    public String editActivity(@PathVariable Long id, Model model,Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        model.addAttribute("activity", activityService.getActivity(id));
        return "editactivity";
    }
}
