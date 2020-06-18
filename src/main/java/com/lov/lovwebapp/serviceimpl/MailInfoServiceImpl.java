package com.lov.lovwebapp.serviceimpl;

import com.lov.lovwebapp.model.MailerInfo;
import com.lov.lovwebapp.model.User;
import com.lov.lovwebapp.repo.MailerInfoRepo;
import com.lov.lovwebapp.service.MailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MailInfoServiceImpl implements MailInfoService {

    private MailerInfoRepo mailerInfoRepo;

    @Autowired
    public MailInfoServiceImpl(MailerInfoRepo mailerInfoRepo) {
        this.mailerInfoRepo = mailerInfoRepo;
    }

    @Override
    public MailerInfo getMailerInfoByUser(User user) {
        return mailerInfoRepo.findByUser(user);
    }

    @Override
    public void saveMailerInfo(MailerInfo mailerInfo) {
        mailerInfoRepo.save(mailerInfo);
    }

    @Override
    public void delete(MailerInfo mailerInfo) {
        mailerInfoRepo.delete(mailerInfo);
    }

}
