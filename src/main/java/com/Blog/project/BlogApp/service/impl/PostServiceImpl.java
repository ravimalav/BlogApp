package com.Blog.project.BlogApp.service.impl;

import com.Blog.project.BlogApp.entity.Category;
import com.Blog.project.BlogApp.entity.Post;
import com.Blog.project.BlogApp.entity.User;
import com.Blog.project.BlogApp.exceptions.ResourceNotFoundException;
import com.Blog.project.BlogApp.payloads.PostDto;
import com.Blog.project.BlogApp.payloads.PostResponse;
import com.Blog.project.BlogApp.repository.CategoryRepo;
import com.Blog.project.BlogApp.repository.PostRepo;
import com.Blog.project.BlogApp.repository.UserRepository;
import com.Blog.project.BlogApp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

//import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired

    private UserRepository userRepository;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto,  Integer userId,Integer categoryId) {

        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","category id",categoryId));

       Post post= this.modelMapper.map(postDto,Post.class);
       post.setImageName("default.png");
       post.setAddedDate(new Date());
       post.setUser(user);
       post.setCategory(category);
       Post newPost=this.postRepo.save(post);

        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId)
    {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(post.getContent());
        post.setImageName(post.getImageName());
        Post updatedPost=this.postRepo.save(post);

        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
//        if()
//        {
//            sort=;
//        }
//        else {
//            sort=;
//        }
        Pageable p=PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost=this.postRepo.findAll(p);

       List<Post> allPosts=pagePost.getContent();
       List<PostDto> postDtos=allPosts.stream()
               .map((post)->this.modelMapper.map(post,PostDto.class))
               .collect(Collectors.toList());
          PostResponse postResponse=new PostResponse();
          postResponse.setContent(postDtos);
          postResponse.setPageNumber(pagePost.getNumber());
          postResponse.setPageSize(pagePost.getSize());
          postResponse.setTotalElement(pagePost.getTotalElements());
          postResponse.setTotalPages(pagePost.getTotalPages());
          postResponse.setLastpage(pagePost.isLast());

          return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
        PostDto postDto=this.modelMapper.map(post,PostDto.class);

        return postDto;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","user id",userId));
        List<Post> posts=this.postRepo.findByUser(user);
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));
        List<Post> posts=this.postRepo.findByCategory(cat);
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }


    @Override
    public List<PostDto> searchPost(String keyword) {
       List<Post> posts= this.postRepo.findByTitleContenting("%"+keyword+"%");

        return posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }
}
