package com.Blog.project.BlogApp.service;

import com.Blog.project.BlogApp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    // create category
    CategoryDto createCategory(CategoryDto category);

    //update category
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    //get category by Id

    CategoryDto getCategoryById(Integer categoryId);

    //get Allcategory
    List<CategoryDto> getAllCategories();

    //delete category
    void deleteCategory(Integer categoryId);


}
