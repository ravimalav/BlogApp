package com.Blog.project.BlogApp.service.impl;

import com.Blog.project.BlogApp.entity.Comment;
import com.Blog.project.BlogApp.entity.Post;
import com.Blog.project.BlogApp.exceptions.ResourceNotFoundException;
import com.Blog.project.BlogApp.payloads.CommentDto;
import com.Blog.project.BlogApp.repository.CommentRepo;
import com.Blog.project.BlogApp.repository.PostRepo;
import com.Blog.project.BlogApp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
         Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
         Comment comment=this.modelMapper.map(commentDto,Comment.class);
         comment.setPost(post);
        Comment savedComment= this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment,CommentDto.class);
         }

    @Override
    public void deleteComment(Integer commentId) {
        Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","comment id",commentId));
        this.commentRepo.delete(com);
    }
}
