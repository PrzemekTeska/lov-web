package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.GoalForInfo;
import com.lov.lovwebapp.model.MailerInfo;
import com.lov.lovwebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class MailInformator {

    private MailSenderService mailSenderService;
    private MailInfoService mailInfoService;
    private UserService userService;

    @Autowired
    public MailInformator(MailSenderService mailSenderService, MailInfoService mailInfoService, UserService userService) {
        this.mailSenderService = mailSenderService;
        this.mailInfoService = mailInfoService;
        this.userService=userService;
    }

    @Scheduled(cron = "0 0 12 * * MON")
    public void createEmailInformationWeekly(){
        List<User> allUsers = userService.getAllUsers();
        for(User user:allUsers){
            if(user.getNotificationFrequency().equalsIgnoreCase("weekly")){
                String text = createText(user);
                sendMail(text, user,"weekly");
            }
        }
    }

    @Scheduled(cron = "0 0 13 1/15 * *")
    public void createEmailInformationTwice(){
        List<User> allUsers = userService.getAllUsers();
        for(User user:allUsers){
            if(user.getNotificationFrequency().equalsIgnoreCase("oetw")){
                String text =  createText(user);
                sendMail(text, user,"two weeks");
            }
        }
    }

    @Scheduled(cron = "0 0 14 1 * *")
    public void createEmailInformationMonthly(){
        List<User> allUsers = userService.getAllUsers();
        for(User user:allUsers){
            if(user.getNotificationFrequency().equalsIgnoreCase("monthly")){
               String text = createText(user);
                sendMail(text, user,"monthly");
            }
        }
    }

    private String createText(User user) {
        MailerInfo mailerInfo = mailInfoService.getMailerInfoByUser(user);
        List<GoalForInfo> goalForInfoList = mailerInfo.getGoalForInfo();
        String text = "";
        List<GoalForInfo> accomplishedGoals = goalForInfoList.stream().filter(e -> !e.isActive()).collect(Collectors.toList());
        if (!accomplishedGoals.isEmpty()) {
            text += "You have accomplished the following goals:\n";
            for (GoalForInfo goalForInfo : accomplishedGoals) {
                text +="Goal name: " +goalForInfo.getGoalName() +", percentage: "+goalForInfo.getPercentage()+"\n";
            }
        }
        List<GoalForInfo> notAccomplishedGoals = goalForInfoList.stream().filter(GoalForInfo::isActive).collect(Collectors.toList());
        if (!notAccomplishedGoals.isEmpty()) {
            text += "You have not accomplished the following goals yet:\n";
            for (GoalForInfo goalForInfo : notAccomplishedGoals) {
                text +="Goal name: " +goalForInfo.getGoalName() +", current percentage: "+goalForInfo.getPercentage()+"\n";
            }

        }
        return text;
    }

    private void sendMail(String text, User user, String subject) {
        try {
            mailSenderService.sendMail(user.getEmail(),
                    "Your "+subject+" summary",
                    text,
                    false
            );

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
