package com.blog.service;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;

public interface PostService {

    /**
     * Method for creating the new post
     *
     * @param postDto
     * @return PostDto
     */
    PostDto createPost(PostDto postDto);

    /**
     * Method for retrieving all posts from DB
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param orderBy
     * @return PostResponse
     */
    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String orderBy);

    /**
     * Finding the post by provided ID
     *
     * @param id
     * @return PostDto
     */
    PostDto getPostById(Long id);

    /**
     * Updating the post
     *
     * @param postDto
     * @param id
     * @return PostDto
     */
    PostDto updatePost(PostDto postDto, Long id);

    /**
     * Method for deleting the post
     *
     * @param id
     */
    void deletePost(Long id);
}
