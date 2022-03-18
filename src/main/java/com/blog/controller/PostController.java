package com.blog.controller;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;
import com.blog.entity.Post;
import com.blog.service.PostService;
import com.blog.utils.AppConstants;
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
            log.info("Post created successfully!");
            return new ResponseEntity<>(service.createPost(postDto), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating the Post. Please see the info below:");
            log.info(e.getMessage().toUpperCase(Locale.ROOT));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "orderBy", defaultValue = AppConstants.DEFAULT_ORDER_BY, required = false) String orderBy
    ) {

        try {
            log.info("Finding all posts from the database in {} order...", orderBy);
            return new ResponseEntity<>(service.getAllPosts(pageNumber, pageSize, sortBy, orderBy), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Couldn't fetch posts from the database.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {

        PostDto postResponse = service.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        service.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully.", HttpStatus.OK);
    }

}
