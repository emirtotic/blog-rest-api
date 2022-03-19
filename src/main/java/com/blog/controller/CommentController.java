package com.blog.controller;

import com.blog.dto.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> postComment(
            @PathVariable(name = "postId") Long postId,
            @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(service.createComment(postId, commentDto), HttpStatus.CREATED);
    }
}
