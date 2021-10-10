package com.example.jwtsecuritydatabase.Service;

import com.example.jwtsecuritydatabase.Entity.Post;
import com.example.jwtsecuritydatabase.Exception.PostNotFoundException;
import com.example.jwtsecuritydatabase.Repository.PostRepo;
import com.example.jwtsecuritydatabase.Request.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private UserService userService;
    @Autowired
    private PostRepo postRepo;

//    public void createPost (PostRequest postRequest){
//        Post post = new Post();
//        post.setTitle(postRequest.getTitle());
//        post.setContent(postRequest.getContent());
//        User username = userService.getCurrentUser().orElseThrow(() ->
//                new IllegalArgumentException("No user logged in"));
//        post.setUsername(username.getUsername());
//        post.setCreatedOn(Instant.now());
//        postRepo.save(post);
//    }

    @Transactional
    public void createPost(PostRequest postRequest) {
        Post post = mapFromDtoToPost(postRequest);
        postRepo.save(post);
    }

    public Object showAllPosts() {
        List<Post> posts = postRepo.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    public Object readSinglePost(Long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    private PostRequest mapFromPostToDto(Post post) {
        PostRequest postRequest = new PostRequest();
        postRequest.setId(post.getId());
        postRequest.setTitle(post.getTitle());
        postRequest.setContent(post.getContent());
        postRequest.setUsername(post.getUsername());
        return postRequest;
    }

    private Post mapFromDtoToPost(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
//        User loggedInUser = userService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
//        post.setUsername(loggedInUser.getUsername());
        post.setUsername("Author");
        post.setUpdatedOn(Instant.now());
        return post;
    }
}
