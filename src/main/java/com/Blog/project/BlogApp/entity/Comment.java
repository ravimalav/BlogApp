package com.Blog.project.BlogApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.Date;

@Table(name="comments")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String content;
    private Date addedDate;

    private Time addedTime;

    @ManyToOne
    private  Post post;

}
