package com.example.online_course.controller;

import com.example.online_course.dto.request.CategoryRequest;
import com.example.online_course.dto.response.ApiResponse;
import com.example.online_course.dto.response.CategoryResponse;
import com.example.online_course.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> create(@RequestBody CategoryRequest categoryRequest) {
        return new ApiResponse<>("Create category successfully", 200, categoryService.createCategory(categoryRequest));
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategory() {
        return new ApiResponse<>("Get categories successfully", 200, categoryService.getAllCategory());
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return new ApiResponse<>("Get category successfully", 200, categoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Long id) {
        return new ApiResponse<>("Update category successfully", 200, categoryService.updateCategory(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ApiResponse<>("Category deleted successfully", 200, null);
    }

}