package com.example.jwtsecuritydatabase.Repository;

import com.example.jwtsecuritydatabase.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name); // return a role by the role we pass
}
