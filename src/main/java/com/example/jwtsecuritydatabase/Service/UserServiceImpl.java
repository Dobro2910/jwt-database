package com.example.jwtsecuritydatabase.Service;

import com.example.jwtsecuritydatabase.Entity.AppUser;
import com.example.jwtsecuritydatabase.Entity.Role;
import com.example.jwtsecuritydatabase.Repository.RoleRepo;
import com.example.jwtsecuritydatabase.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor // create constructor and make sure all argument are pass into the constructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByUsername(username); // find username in the database
        if(appUser == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User nor found");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> { //distribute different authority to different role
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving new user {} to the database", appUser.getUsername());
//        if (userRepo.existsByUserName(appUser.getUsername())) {
//            throw new IllegalStateException("User with given username already exist");
//        } else {
                appUser.setPassword(passwordEncoder.encode(appUser.getPassword())); //encode password of the user and set it as the password in the database
                return userRepo.save(appUser);
//            }
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding role {} to user {}", roleName, username);
        AppUser appUser = userRepo.findByUsername(username); //get user
        Role role = roleRepo.findByName(roleName); // get role
        appUser.getRoles().add(role); //add role to user
    }

    @Override
    public AppUser getUser(String username) {
        log.info("fetching user {}", username);
        return userRepo.findByUsername(username); //return username
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("fetching all users");
        return userRepo.findAll(); // return all users
    }

//    @Override
//    public Optional<User> getCurrentUser() {
//        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return Optional.of(principal);
//    }

//    @Override
//    public AuthenticationResponse login(LoginRequest loginRequest) {
//        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
//                loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        String authenticationToken = jwtProvider.generateToken(authenticate);
//        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
//    }

}
