package com.example.online_course.service;

import com.example.online_course.dto.request.CategoryRequest;
import com.example.online_course.dto.response.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(
            CategoryRequest categoryRequest,
            MultipartFile file
    ) throws IOException;

    CategoryResponse getCategoryById(Long id);
}