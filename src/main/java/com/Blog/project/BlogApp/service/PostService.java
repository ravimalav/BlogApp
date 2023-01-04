package com.Blog.project.BlogApp.service;

import com.Blog.project.BlogApp.entity.Post;
import com.Blog.project.BlogApp.payloads.CategoryDto;
import com.Blog.project.BlogApp.payloads.PostDto;
import com.Blog.project.BlogApp.payloads.PostResponse;
import com.Blog.project.BlogApp.payloads.UserDto;

import java.util.List;

public interface PostService {

    //create
    PostDto createPost(PostDto postDto, Integer userId,Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto,Integer postId);

    //delete
    void deletePost(Integer postId);

    //get All post

    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String SortDir);
    //get Post by id
    PostDto getPostById(Integer postId);

    //get all posts by a user
    List<PostDto> getPostsByUser(Integer userId);

    //get all posts of a category
    List<PostDto> getPostsByCategory(Integer categoryId);

    //search a post

    List<PostDto> searchPost(String keyword);

}
