package com.lov.lovwebapp.service;

import com.lov.lovwebapp.model.MailerInfo;
import com.lov.lovwebapp.model.User;

public interface MailInfoService {
    MailerInfo getMailerInfoByUser(User user);
    void saveMailerInfo(MailerInfo mailerInfo);
    void delete(MailerInfo mailerInfo);
}
