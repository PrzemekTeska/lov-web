package com.lov.lovwebapp.controller;

import com.lov.lovwebapp.model.Activity;
import com.lov.lovwebapp.model.Goal;
import com.lov.lovwebapp.model.Penalty;
import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.service.ActivityService;
import com.lov.lovwebapp.service.GoalService;
import com.lov.lovwebapp.service.PenaltyService;
import com.lov.lovwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class PenaltyController {

    private UserService userService;
    private PenaltyService penaltyService;
    private GoalService goalService;
    private ActivityService activityService;

    @Autowired
    public PenaltyController(UserService userService, PenaltyService penaltyService, GoalService goalService,ActivityService activityService) {
        this.userService = userService;
        this.penaltyService = penaltyService;
        this.goalService = goalService;
        this.activityService=activityService;
    }

    @RequestMapping("/penalties")
    public String penalties(Model model, Principal principal) {
        long id=userService.getUserByName(principal.getName()).getId();
        model.addAttribute("penaltyList", penaltyService.getAllPenalties(id));
        List<Penalty> activePenaltyList=penaltyService.getAllActivePenalties(id);
        model.addAttribute("activePenaltyList",activePenaltyList);

        model.addAttribute("checkActivePenalties", !activePenaltyList.isEmpty());
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "penalties";
    }

    @RequestMapping("/redirectToAddPenalty")
    public ModelAndView redirectToAddPenalty(Principal principal, Model model) {
        List<Goal> goalList = goalService.getAllGoals(userService.getUserByName(principal.getName()).getId());
        List<Activity> activityList = activityService.getAllActivities(userService.getUserByName(principal.getName()).getId());
        if (!goalList.isEmpty()) {
            if(!activityList.isEmpty()) return new ModelAndView("redirect:/addpenalty");
            else return new ModelAndView("redirect:/addactivityfail?warning=a_penalty&endpoint=penalty");
        }
        return new ModelAndView("redirect:/addgoalnoactivity?warning=a_penalty&endpoint=penalty");
    }

    @RequestMapping("/addactivityfail")
    public ModelAndView addGoalNoActivity(Model model, @RequestParam String warning, @RequestParam String endpoint, Principal principal) {
        warning = warning.replace("_"," ");
        model.addAttribute("warning",warning);
        model.addAttribute("endpoint",endpoint);
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        return new ModelAndView("addactivityfail", "activity", new Activity());
    }

    @RequestMapping("/saveactivityfail/{endpoint}")
    public ModelAndView saveActivityFail(@PathVariable String endpoint, Activity activity) {
        activityService.addActivity(activity);
        switch (endpoint) {
            case "reward":
                return new ModelAndView("redirect:/addreward");

            case "penalty":
                return new ModelAndView("redirect:/addpenalty");

            default:
                return new ModelAndView("redirect:/activities");
        }
    }

    @RequestMapping("/addpenalty")
    public ModelAndView addPenalty(Model model, Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        return new ModelAndView("addpenalty", "penalty", new Penalty());
    }

    @RequestMapping("/savepenalty")
    public ModelAndView savePenalty(Penalty penalty) {
        penaltyService.addNewPenalty(penalty);
        return new ModelAndView("redirect:/penalties");
    }

    @RequestMapping("/redirectToPenalties")
    public ModelAndView redirectToPenalties() {
        return new ModelAndView("redirect:/penalties");
    }

    @RequestMapping(value = "/penalties/delete/{id}", method = RequestMethod.GET)
    public String deletePenalty(@PathVariable Long id) {
        penaltyService.deletePenalty(id);
        return "redirect:/penalties";
    }

    @RequestMapping(value = "penalties/editpenalty/{id}", method = RequestMethod.POST)
    public String updateReward(@PathVariable Long id, Penalty penalty) {
        penaltyService.updatePenalty(penalty);
        return "redirect:/penalties";
    }

    @RequestMapping("/editpenalty/{id}")
    public String editReward(@PathVariable Long id, Model model,Principal principal) {
        model.addAttribute("goalList", goalService.getAllGoals(userService.getUserByName(principal.getName()).getId()));
        model.addAttribute("penalty", penaltyService.getPenalty(id));
        return "editpenalty";
    }
}
