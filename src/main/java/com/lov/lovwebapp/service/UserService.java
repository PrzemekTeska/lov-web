package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface UserService extends UserDetailsService {

    void saveUser(User user, HttpServletRequest request);

    void saveUser(User user);

    void verifyToken( String token);

    List<User> getAllUsers();

    User getUserByName(String username);

    User getUserByID(long id);

    boolean checkData(User user);

    boolean checkIfTakenEdit(User user);

    void updateUser(User user, Principal principal);

}
