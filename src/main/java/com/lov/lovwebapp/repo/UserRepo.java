package com.lov.lovwebapp.repo;

import com.lov.lovwebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findAllByUsername(String username);
}
