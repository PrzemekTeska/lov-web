package com.lov.lovwebapp.controller;

import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.service.MailInfoService;
import com.lov.lovwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ProfileController {

    private UserService userService;
    private MailInfoService mailInfoService;

    @Autowired
    public ProfileController(UserService userService,MailInfoService mailInfoService) {
        this.userService = userService;
        this.mailInfoService=mailInfoService;
    }

    @RequestMapping("/redirectToProfile")
    public ModelAndView redirectToProfile() {
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping("/redirectToEditProfile")
    public ModelAndView redirectToEditProfile() {
        return new ModelAndView("redirect:/editprofile");
    }

    @RequestMapping("/editprofile")
    public String editProfile(Principal principal, Model model) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "editprofile";
    }

    @RequestMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
    public String editProfile(User user, Principal principal) {
        if (userService.checkData(user)) {
            if (!userService.checkIfTakenEdit(user)) {
                userService.updateUser(user, principal);
            return "redirect:/redirectToLogout";
            }
            return "redirect:/editprofile-taken";
        }
        return "redirect:/editprofile-wrongdata";
    }

    @RequestMapping("/editprofile-taken")
    public String editProfileTaken(Model model, Principal principal) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "editprofile-taken";
    }

    @RequestMapping("/editprofile-wrongdata")
    public String editProfileWrongData(Model model, Principal principal) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "editprofile-wrongdata";
    }
}