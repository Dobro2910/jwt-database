package com.example.jwtsecuritydatabase.Repository;

import com.example.jwtsecuritydatabase.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username); // select user by their username which is the username we pass
//    boolean existsByUserName(String userName);
}
