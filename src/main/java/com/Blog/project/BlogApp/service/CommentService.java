package com.Blog.project.BlogApp.service;

import com.Blog.project.BlogApp.payloads.CommentDto;

public interface CommentService {
     CommentDto createComment(CommentDto commentDto,Integer postId);

     void deleteComment(Integer commentId);

}
