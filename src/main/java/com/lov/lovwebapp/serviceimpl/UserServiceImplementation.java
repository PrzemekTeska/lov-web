package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.UserRepo;
import com.lov.lovwebapp.service.MailSenderService;
import com.lov.lovwebapp.service.UserService;
import com.lov.lovwebapp.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Primary
@Service
public class UserServiceImplementation implements UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenService verificationTokenService;
    private MailSenderService mailSenderService;

    @Autowired
    public UserServiceImplementation(UserRepo userRepo, PasswordEncoder passwordEncoder, VerificationTokenService verificationTokenService, MailSenderService mailSenderService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenService = verificationTokenService;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public void saveUser(User user, HttpServletRequest request) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPasswordRepeat(passwordEncoder.encode(user.getPasswordRepeat()));
        user.setNotificationFrequency("disabled");
        userRepo.save(user);

        String url = "http://" + request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                "/verify-token?token=" + verificationTokenService.sendToken(user);

        try {
            mailSenderService.sendMail(user.getEmail(),
                    "Verification token",
                    url,
                    false
            );
        } catch (MessagingException e) {
        }
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public void verifyToken(String token) {
        User user = verificationTokenService.verifyToken(token);
        userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByName(String username) {
        return userRepo.findAllByUsername(username);
    }

    @Override
    public User getUserByID(long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findAllByUsername(s);
    }

    @Override
    public boolean checkData(User user) {
        String patternPassword="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{9,}$";
        String patternUsername="^(?=\\S+$).{3,}$";

        return user.getPassword().matches(patternPassword) && user.getPassword().equals(user.getPasswordRepeat())
                && user.getUsername().matches(patternUsername);
    }


    @Override
    public boolean checkIfTakenEdit(User user) {
        List<User> userList = getAllUsers();
        if(userList.remove(user)) return userList.stream().anyMatch(e -> e.getUsername().equals(user.getUsername()) || e.getEmail().equals(user.getEmail()));
        return false;
    }

    @Override
    public void updateUser(User user, Principal principal) {
        User userBefore = userRepo.findAllByUsername(principal.getName());
        user.setId(userBefore.getId());
        user.setEnabled(userBefore.isEnabled());
        user.setEmail(userBefore.getEmail());
        user.setPoints(userBefore.getPoints());
        user.setNotificationFrequency(user.getNotificationFrequency());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPasswordRepeat(passwordEncoder.encode(user.getPasswordRepeat()));
        userRepo.save(user);
    }
}

