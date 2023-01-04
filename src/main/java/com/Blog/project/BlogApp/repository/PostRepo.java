package com.Blog.project.BlogApp.repository;

import com.Blog.project.BlogApp.entity.Category;
import com.Blog.project.BlogApp.entity.Post;
import com.Blog.project.BlogApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    //to find all post by a user
    List<Post> findByUser(User user);
    //to find all post of a category
    List<Post> findByCategory(Category category);
    @Query("select p from Post p where p.title like:key")
    List<Post> findByTitleContenting(@Param("key") String title);
}
