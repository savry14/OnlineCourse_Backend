package com.example.online_course.service;

import com.example.online_course.dto.request.CategoryRequest;
import com.example.online_course.dto.response.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    public CategoryResponse createCategory(CategoryRequest categoryRequest);
    public List<CategoryResponse> getAllCategory();
    CategoryResponse getCategoryById(Long id);
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);
    void deleteCategory(Long id);
}