package com.blog.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        name = "post",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"title"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;
}
