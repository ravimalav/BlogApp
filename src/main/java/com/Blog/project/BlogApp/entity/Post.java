package com.Blog.project.BlogApp.entity;

import com.Blog.project.BlogApp.payloads.CommentDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="posts")
@Getter
@NoArgsConstructor
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;
    @NotEmpty
    @Size(min = 4 ,message = "please enter more than four character")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "content should have more than 10 character")
    private  String content;

    private String imageName;

    private Date addedDate;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy ="post",cascade = CascadeType.ALL)

    private Set<Comment> comments=new HashSet<>();
}
