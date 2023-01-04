package com.Blog.project.BlogApp.repository;

import com.Blog.project.BlogApp.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
