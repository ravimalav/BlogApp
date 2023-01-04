package com.Blog.project.BlogApp.controller;

import com.Blog.project.BlogApp.payloads.ApiResponse;
import com.Blog.project.BlogApp.payloads.CategoryDto;
import com.Blog.project.BlogApp.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto createCategory=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer catid )
    {
        CategoryDto updateCategory=this.categoryService.updateCategory(categoryDto,catid);
        return  ResponseEntity.ok(updateCategory);
    }
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId)
    {
      this.categoryService.deleteCategory(categoryId);
      return  new ResponseEntity<>(new ApiResponse("category deleted successfully",true),HttpStatus.OK);
    }

    //getCategoryById
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId)
    {
        CategoryDto categoryDto=this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }

    //getAllcategory
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories()
    {
        List<CategoryDto> categories=this.categoryService.getAllCategories();
        return  ResponseEntity.ok(categories);
    }
}
