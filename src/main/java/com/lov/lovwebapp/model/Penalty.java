package com.lov.lovwebapp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String contents;
    private int failedInARow;
    private int failedInARowLimit;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Goal goal;

    public Penalty() {

    }

    public Penalty(String contents,int failedInARow ,int failedInARowLimit, Goal goal) {
        this.contents=contents;
        this.failedInARow=failedInARow;
        this.failedInARowLimit=failedInARowLimit;
        this.goal=goal;
    }

    public int getFailedInARowLimit() {
        return failedInARowLimit;
    }

    public void setFailedInARowLimit(int failedInARowLimit) {
        this.failedInARowLimit = failedInARowLimit;
    }

    public int getFailedInARow() {
        return failedInARow;
    }

    public void setFailedInARow(int failedInARow) {
        this.failedInARow = failedInARow;
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
