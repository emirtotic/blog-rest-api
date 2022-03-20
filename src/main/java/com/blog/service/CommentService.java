package com.blog.service;

import com.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    /**
     * Method for creating a new comment
     *
     * @param postId
     * @param commentDto
     * @return CommentDto
     */
    CommentDto createComment(long postId, CommentDto commentDto);

    /**
     * Getting All comments for post with provided ID
     *
     * @param postId
     * @return List<CommentDto>
     */
    List<CommentDto> getCommentsByPostId(Long postId);

    /**
     * Finding a method by comment ID
     *
     * @param postId
     * @param commentId
     * @return CommentDto
     */
    CommentDto findCommentById(Long postId, Long commentId);

    /**
     * Method for updating the existing comment
     *
     * @param postId
     * @param commentId
     * @param commentDto
     * @return CommentDto
     */
    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

    /**
     * Method for deleting a comment
     *
     * @param postId
     * @param commentId
     */
    void deleteComment(Long postId, Long commentId);

}
