package com.lov.lovwebapp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String activityName;
    private String activityUnit;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Goal activityGoal;
    private int activityPoints;
    private int activityAmount;
    private String frequency;
    private int counter;
    private LocalDate startDate;
    private String counterString;
    private LocalDateTime endDateTime;

    public Activity(String activityName, String activityUnit, Goal activityGoal, int activityPoints,
                    int activityAmount, String frequency, int counter, LocalDateTime endDateTime) {
        this.activityName = activityName;
        this.activityUnit = activityUnit;
        this.activityGoal = activityGoal;
        this.activityPoints = activityPoints;
        this.activityAmount = activityAmount;
        this.frequency = frequency;
        this.counter = counter;
        this.endDateTime = endDateTime;
    }

//    public Activity(long id, String activityName, String activityUnit, Goal activityGoal, int activityPoints, int activityAmount) {
//        this.id = id;
//        this.activityName = activityName;
//        this.activityUnit = activityUnit;
//        this.activityGoal = activityGoal;
//        this.activityPoints = activityPoints;
//        this.activityAmount = activityAmount;
//    }

    public Activity() {
    }

    public String getCounterString() {
        return counterString;
    }

    public void setCounterString(String counterString) {
        this.counterString = counterString;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityUnit() {
        return activityUnit;
    }

    public void setActivityUnit(String activityUnit) {
        this.activityUnit = activityUnit;
    }

    public Goal getActivityGoal() {
        return activityGoal;
    }

    public void setActivityGoal(Goal goal) {
        this.activityGoal = goal;
    }

    public int getActivityPoints() {
        return activityPoints;
    }

    public void setActivityPoints(int activityPoints) {
        this.activityPoints = activityPoints;
    }

    public int getActivityAmount() {
        return activityAmount;
    }

    public void setActivityAmount(int activityAmount) {
        this.activityAmount = activityAmount;
    }
}
