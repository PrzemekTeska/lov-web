package com.lov.lovwebapp.repo;

import com.lov.lovwebapp.model.MailerInfo;
import com.lov.lovwebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MailerInfoRepo extends JpaRepository<MailerInfo, Long> {
    MailerInfo findByUser(User user);
}
