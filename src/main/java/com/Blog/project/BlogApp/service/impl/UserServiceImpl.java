package com.Blog.project.BlogApp.service.impl;

import com.Blog.project.BlogApp.configuration.ApiConstant;
import com.Blog.project.BlogApp.entity.Role;
import com.Blog.project.BlogApp.payloads.UserDto;
import com.Blog.project.BlogApp.repository.RoleRepo;
import com.Blog.project.BlogApp.repository.UserRepository;
import com.Blog.project.BlogApp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.Blog.project.BlogApp.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import com.Blog.project.BlogApp.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

//    @Override
//    public UserDto registerUser(UserDto userDto) {
//       User user= this.modelMapper.map(userDto,User.class);
//       user.setPassword(this.passwordEncoder.encode(user.getPassword()));
//       Role role=this.roleRepo.findById(ApiConstant.NORMAL_USER).get();
//       user.getRoles().add(role);
//       User newUser=this.userRepository.save(user);
//        return this.modelMapper.map(newUser,UserDto.class);
//    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user=this.dtoToUser(userDto);

        User savedUser=this.userRepository.save(user);

        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id",userId));

        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());

        User updatedUser=this.userRepository.save(user);
        UserDto userDto1=this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
       List<User> users=this.userRepository.findAll();
       //converting user into userList(users)
        List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        this.userRepository.delete(user);
    }

    public User dtoToUser(UserDto userDto)
    {
        User user=this.modelMapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(user.getPassword());
//        user.setAbout(user.getAbout());
        return user;
    }

    public UserDto userToDto(User user)
    {
        UserDto userDto=this.modelMapper.map(user,UserDto.class);

        return userDto;
    }
}
