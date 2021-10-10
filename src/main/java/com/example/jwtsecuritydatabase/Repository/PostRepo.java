package com.example.jwtsecuritydatabase.Repository;

import com.example.jwtsecuritydatabase.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {


}
