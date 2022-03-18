package com.blog.service.impl;

import com.blog.dto.PostDto;
import com.blog.entity.Post;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = convertDTOObjectToEntityObject(postDto);
        Post newPost = postRepository.save(post);

        return convertEntityObjectToDTOObject(newPost);
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::convertEntityObjectToDTOObject).collect(Collectors.toList());
    }

    private Post convertDTOObjectToEntityObject(PostDto postDto) {

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;
    }

    private PostDto convertEntityObjectToDTOObject(Post post) {

        PostDto postResponse = new PostDto();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setDescription(post.getDescription());
        postResponse.setContent(post.getContent());

        return postResponse;
    }

}
