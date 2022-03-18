package com.blog.service;

import com.blog.dto.PostDto;
import com.blog.entity.Post;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();

}
