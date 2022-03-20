package com.blog.service.impl;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CommentServiceImpl commentServiceImpl;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, CommentServiceImpl commentServiceImpl, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentServiceImpl = commentServiceImpl;
        this.modelMapper = modelMapper;
    }

    /**
     * Method for creating the new post
     *
     * @param postDto
     * @return PostDto
     */
    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = convertDTOObjectToEntityObject(postDto);
        Post newPost = postRepository.save(post);

        return convertEntityObjectToDTOObject(newPost);
    }

    /**
     * Method for retrieving all posts from DB
     *
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param orderBy
     * @return PostResponse
     */
    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String orderBy) {

        Sort sort = orderBy.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(this::convertEntityObjectToDTOObject).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    /**
     * Finding the post by provided ID
     *
     * @param id
     * @return PostDto
     */
    @Override
    public PostDto getPostById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        log.info("Post with id {} found.", id);
        return convertEntityObjectToDTOObject(post);
    }

    /**
     * Updating the post
     *
     * @param postDto
     * @param id
     * @return PostDto
     */
    @Override
    public PostDto updatePost(PostDto postDto, Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        return convertEntityObjectToDTOObject(updatedPost);
    }

    /**
     * Method for deleting the post
     *
     * @param id
     */
    @Override
    public void deletePost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    /**
     * Method for converting PostDto object to Post entity
     *
     * @param postDto
     * @return
     */
    private Post convertDTOObjectToEntityObject(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    /**
     * Method for converting Post Entity to PostDto object
     *
     * @param post
     * @return
     */
    private PostDto convertEntityObjectToDTOObject(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

}
