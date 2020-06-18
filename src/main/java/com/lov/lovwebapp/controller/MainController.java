package com.lov.lovwebapp.controller;

import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.service.PointSorter;
import com.lov.lovwebapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/ranking")
    public String main(Model model, Principal principal) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("rankingList", getRanking());
        model.addAttribute("currentUserRank", getCurrentUserRank(principal.getName()));
        return "ranking";
    }

    private List<User> getRanking(){

        List<User> allUsers = userService.getAllUsers();

        allUsers.sort(new PointSorter());

        return allUsers;
    }

    int getCurrentUserRank(String username){
        List<User> ranking = getRanking();
        for(int i = 0; i<ranking.size(); i++){
            if(ranking.get(i).getUsername().equals(username)){
                return i + 1;
            }
        }
        return 0;
    }
}
