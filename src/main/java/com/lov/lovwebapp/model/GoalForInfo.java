package com.lov.lovwebapp.model;

import javax.persistence.*;

@Entity
public class GoalForInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String goalName;
    private int percentage;
    private boolean active;
    @ManyToOne
    @JoinColumn
    private MailerInfo parent;

    public GoalForInfo() {
    }

    public GoalForInfo(String goalName, int percentage, boolean active, MailerInfo mailerInfo) {
        this.goalName=goalName;
        this.percentage=percentage;
        this.active=active;
        this.parent=mailerInfo;
    }

    public MailerInfo getParent() {
        return parent;
    }

    public void setParent(MailerInfo parent) {
        this.parent = parent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
