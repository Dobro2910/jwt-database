package com.example.jwtsecuritydatabase.Controller;

import com.example.jwtsecuritydatabase.Request.PostRequest;
import com.example.jwtsecuritydatabase.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        postService.createPost(postRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostRequest>> showAllPosts() {
        return new ResponseEntity(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostRequest> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity(postService.readSinglePost(id), HttpStatus.OK);
    }
}
