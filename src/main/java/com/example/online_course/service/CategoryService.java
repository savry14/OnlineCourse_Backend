package com.example.online_course.service;



import com.example.online_course.dto.request.CategoryRequest;
import com.example.online_course.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long categoryId);

    List<CategoryResponse> getAllCategories();

    CategoryResponse updateCategory(
            Long categoryId,
            CategoryRequest request
    );

    void deleteCategory(Long categoryId);
}
