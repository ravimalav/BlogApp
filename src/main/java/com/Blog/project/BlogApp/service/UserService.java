package com.Blog.project.BlogApp.service;

import com.Blog.project.BlogApp.entity.User;
import com.Blog.project.BlogApp.payloads.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
//    UserDto registerUser(UserDto user);

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
