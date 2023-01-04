package com.Blog.project.BlogApp.repository;

import com.Blog.project.BlogApp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
