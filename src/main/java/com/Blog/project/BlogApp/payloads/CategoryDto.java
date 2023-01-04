package com.Blog.project.BlogApp.payloads;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty
    @Size(min = 2,message = "Title should be between 2 to 10 characters",max = 10)
    private String categoryTitle;
    @NotEmpty(message = "please provide descrition")
    private String categoryDesc;
}
