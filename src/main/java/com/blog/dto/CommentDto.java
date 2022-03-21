package com.blog.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {

    private Long id;

    @NotEmpty(message = "Comment should not be null or empty.")
    @Size(min = 5, message = "Comment must be at least 5 characters long!")
    private String body;

    @NotEmpty(message = "Email should not be null or empty.")
    @Email
    private String email;

    @NotBlank(message = "Name should not be null or empty.")
    private String name;
}
