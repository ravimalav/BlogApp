package com.Blog.project.BlogApp.payloads;


import com.Blog.project.BlogApp.entity.Role;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 4,message = "name should have more than 4 character",max = 20)
    private  String name;
    @Email(message = "please enter valid email address")
    private  String email;
    @NotEmpty
    @Size(min = 4,message = "password should be between 4 to 10 character",max = 10)
    private  String password;
    @NotEmpty(message = "please add details")
    private  String  about;
    private Set<RoleDto> roles=new HashSet<>();
}
