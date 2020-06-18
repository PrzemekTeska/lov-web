package com.lov.lovwebapp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String contents;
    private int percentage;
    private int percentageLimit;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Goal goal;

    public Reward() {
    }

    public Reward(String contents,int percentage,int percentageLimit,Goal goal) {
        this.contents=contents;
        this.percentage=percentage;
        this.percentageLimit=percentageLimit;
        this.goal=goal;
    }

    public int getPercentageLimit() {
        return percentageLimit;
    }

    public void setPercentageLimit(int percentageLimit) {
        this.percentageLimit = percentageLimit;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
