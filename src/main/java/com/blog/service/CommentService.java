package com.blog.service;

import com.blog.dto.CommentDto;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

}
