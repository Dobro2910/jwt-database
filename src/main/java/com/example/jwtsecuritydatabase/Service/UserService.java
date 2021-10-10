package com.example.jwtsecuritydatabase.Service;

import com.example.jwtsecuritydatabase.Entity.AppUser;
import com.example.jwtsecuritydatabase.Entity.Role;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    AppUser saveUser(AppUser appUser); //save user in the database
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
//    Optional<User> getCurrentUser();
//    AuthenticationResponse login(LoginRequest loginRequest);
}
