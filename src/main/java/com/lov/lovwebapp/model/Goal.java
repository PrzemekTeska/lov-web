package com.lov.lovwebapp.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String goalName;
    private int failedActivityCounter;
    private int succeededActivityCounter;
    private @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate goalStartDate;
    private @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate goalEndDate;
    @ManyToOne
    private User user;

    public Goal(String goalName, LocalDate goalStartDate, LocalDate goalEndDate, User user) {
        this.goalName = goalName;
        this.goalStartDate = goalStartDate;
        this.goalEndDate = goalEndDate;
        this.user=user;
    }

    public int getFailedActivityCounter() {
        return failedActivityCounter;
    }

    public void setFailedActivityCounter(int failedActivityCounter) {
        this.failedActivityCounter = failedActivityCounter;
    }

    public int getSucceededActivityCounter() {
        return succeededActivityCounter;
    }

    public void setSucceededActivityCounter(int succededActivityCounter) {
        this.succeededActivityCounter = succededActivityCounter;
    }

    public Goal(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goal() {
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

    public LocalDate getGoalStartDate() {
        return goalStartDate;

    }

    public void setGoalStartDate(LocalDate goalStartDate) {
        this.goalStartDate = goalStartDate;
    }

    public LocalDate getGoalEndDate() {
        return goalEndDate;
    }

    public void setGoalEndDate(LocalDate goalEndDate) {
        this.goalEndDate = goalEndDate;
    }
}
