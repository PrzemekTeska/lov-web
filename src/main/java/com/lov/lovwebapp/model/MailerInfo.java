package com.lov.lovwebapp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MailerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoalForInfo> goalForInfo=new ArrayList<>();

    public MailerInfo() {
    }

    public MailerInfo(User user) {
        this.user = user;
    }

    public void setGoalForInfo(List<GoalForInfo> goalForInfo) {
        this.goalForInfo = goalForInfo;
    }

    public List<GoalForInfo> getGoalForInfo() {
        return goalForInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
