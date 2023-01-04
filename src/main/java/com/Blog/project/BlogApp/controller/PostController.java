package com.Blog.project.BlogApp.controller;

import com.Blog.project.BlogApp.configuration.ApiConstant;
import com.Blog.project.BlogApp.payloads.ApiResponse;
import com.Blog.project.BlogApp.payloads.CommentDto;
import com.Blog.project.BlogApp.payloads.PostDto;
import com.Blog.project.BlogApp.payloads.PostResponse;
import com.Blog.project.BlogApp.service.CommentService;
import com.Blog.project.BlogApp.service.FileService;
import com.Blog.project.BlogApp.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    //create

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId)
    {
        PostDto createPost=this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }
    // get post by user
    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
    {
        List<PostDto> posts=this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }

    // get post by category
    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<List<PostDto>> gePostByCategory(@PathVariable Integer categoryId)
    {
        List<PostDto> posts=this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = ApiConstant.PAGE_NUMBER,required = false) Integer pageNumber,
                                                    @RequestParam(value="pageSize",defaultValue=ApiConstant.PAGE_SIZE,required=false) Integer pageSize,
                                                    @RequestParam(value ="sortBy",defaultValue = ApiConstant.SORT_BY,required =false) String sortBy,
                                                    @RequestParam(value="sortDir",defaultValue = ApiConstant.PAGE_SIZE,required = false) String sortDir)
    {
        PostResponse posts=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
       return  ResponseEntity.ok(posts);
    }

    //get post by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer pId)
    {
        PostDto post=this.postService.getPostById(pId);
        return new  ResponseEntity<PostDto>(post,HttpStatus.OK);
    }

    // delete Post
     @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId)
     {
        this.postService.deletePost(postId);
         return new ResponseEntity<>(new ApiResponse("post delete successfully" ,true),HttpStatus.OK);
     }

     //update post using post id
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
    {
        PostDto updatePost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }

    // search post by keyword

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword)
    {
        List<PostDto> result=this.postService.searchPost(keyword);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto>uploadPostImage(@RequestParam("image")MultipartFile image,@PathVariable Integer postId) throws IOException {
        PostDto postDto=this.postService.getPostById(postId);
        String fileName=this.fileService.uploadImage(path,image);

        postDto.setImageName(fileName);
        PostDto updatePost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }
    @GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException
    {
        InputStream resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
