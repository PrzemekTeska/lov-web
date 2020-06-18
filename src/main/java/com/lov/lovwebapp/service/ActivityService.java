package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.Activity;

import java.security.Principal;
import java.util.List;

public interface ActivityService {

    List<Activity> getAllActivities(long userId);

    Activity getActivity(long id);

    void addActivity(Activity activity);

    boolean deleteActivity(long id);

    void deleteFailedActivity(long id);

    void deleteCompletedActivity(long id);

    boolean updateActivity(Activity activity);

    void deleteExpiredActivity(Principal principal);
}
