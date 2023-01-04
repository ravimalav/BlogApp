package com.Blog.project.BlogApp.repository;

import com.Blog.project.BlogApp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer>{
}
