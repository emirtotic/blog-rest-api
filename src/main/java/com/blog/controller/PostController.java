package com.blog.controller;

import com.blog.dto.PostDto;
import com.blog.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {

        try {
            return new ResponseEntity<>(service.createPost(postDto), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating the Post. Please see the info below:");
            log.info(e.getMessage().toUpperCase(Locale.ROOT));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {

        try {
            return new ResponseEntity<>(service.getAllPosts(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't fetch posts from the database.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
