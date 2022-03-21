package com.blog.service.impl;

import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Method for creating a new comment
     *
     * @param postId
     * @param commentDto
     * @return CommentDto
     */
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = convertCommentDtoToCommentEntity(commentDto);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Set post to comment entity
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return convertCommentEntityToCommentDto(savedComment);
    }

    /**
     * Getting All comments for post with provided ID
     *
     * @param postId
     * @return List<CommentDto>
     */
    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(this::convertCommentEntityToCommentDto).collect(Collectors.toList());
    }

    /**
     * Finding a method by comment ID
     *
     * @param postId
     * @param commentId
     * @return CommentDto
     */
    @Override
    public CommentDto findCommentById(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post!");
        }

        return convertCommentEntityToCommentDto(comment);
    }

    /**
     * Method for updating the existing comment
     *
     * @param postId
     * @param commentId
     * @param commentDto
     * @return CommentDto
     */
    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post!");
        }

        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return convertCommentEntityToCommentDto(updatedComment);
    }

    /**
     * Method for deleting a comment
     *
     * @param postId
     * @param commentId
     */
    @Override
    public void deleteComment(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post!");
        }

        commentRepository.deleteById(commentId);

    }

    /**
     * Converting Comment Entity to CommentDto object.
     *
     * @param comment
     * @return CommentDto
     */
    public CommentDto convertCommentEntityToCommentDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    /**
     * Converting CommentDto to Comment Entity object.
     *
     * @param commentDto
     * @return Comment
     */
    public Comment convertCommentDtoToCommentEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }

}
