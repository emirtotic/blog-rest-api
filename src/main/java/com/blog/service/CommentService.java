package com.blog.service;

import com.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto findCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

    void deleteComment(Long postId, Long commentId);

}
