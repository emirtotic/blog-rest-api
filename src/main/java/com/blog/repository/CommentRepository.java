package com.blog.repository;

import com.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Retrieving the list of comments for particular post
     *
     * @param postId
     * @return List<Comment>
     */
    List<Comment> findByPostId(Long postId);

}
