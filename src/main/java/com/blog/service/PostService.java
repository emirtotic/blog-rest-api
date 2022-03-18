package com.blog.service;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String orderBy);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePost(Long id);

}
