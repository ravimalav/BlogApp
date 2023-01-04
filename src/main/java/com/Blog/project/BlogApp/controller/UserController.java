package com.Blog.project.BlogApp.controller;

import com.Blog.project.BlogApp.payloads.ApiResponse;
import com.Blog.project.BlogApp.payloads.UserDto;
import com.Blog.project.BlogApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createUserDto=this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //update the user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId)
    {
        UserDto updateUserDto=this.userService.updateUser(userDto,userId);
        return  ResponseEntity.ok(updateUserDto);
    }


    @DeleteMapping("/{userId}")

    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
    {
        this.userService.deleteUser(uid);
//        return ResponseEntity.ok(Map.of("message","user delete successfully"));
        return new ResponseEntity<>(new ApiResponse("user delete successfully",true) ,HttpStatus.OK);
    }

    // Get all user
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getAllUser(@PathVariable("userId") Integer uid)
    {
        return ResponseEntity.ok(this.userService.getUserById(uid));
    }
}
