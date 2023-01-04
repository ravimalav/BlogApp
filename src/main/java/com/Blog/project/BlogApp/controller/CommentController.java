package com.Blog.project.BlogApp.controller;

import com.Blog.project.BlogApp.payloads.ApiResponse;
import com.Blog.project.BlogApp.payloads.CommentDto;
import com.Blog.project.BlogApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/post/{postId}/comments")
           public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,Integer postId)
           {
               CommentDto createComment=this.commentService.createComment(comment,postId);
               return new ResponseEntity<CommentDto>(createComment, HttpStatus.OK);
           }
           @DeleteMapping("/comment/{commentId}")
           public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
           {
               this.commentService.deleteComment(commentId);
               return new ResponseEntity<>(new ApiResponse("comment deleted successfully",true),HttpStatus.OK);
           }
}
