package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.model.VerificationToken;
import com.lov.lovwebapp.repo.VerificationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationTokenService {

    private VerificationTokenRepo verificationTokenRepo;

    @Autowired
    public VerificationTokenService(VerificationTokenRepo verificationTokenRepo) {
        this.verificationTokenRepo = verificationTokenRepo;
    }

    public String sendToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user,token);
        verificationTokenRepo.save(verificationToken);
        return token;
    }

    public User verifyToken(String token) {
        User user = verificationTokenRepo.findByValue(token).getUser();
        user.setEnabled(true);
        return user;
    }

}
