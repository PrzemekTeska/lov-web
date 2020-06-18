package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.User;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class PointSorter implements Comparator<User> {
    @Override
    public int compare(User user1, User user2) {
        return Long.compare(user2.getPoints(), user1.getPoints());
    }
}
