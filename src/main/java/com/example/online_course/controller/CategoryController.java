package com.example.online_course.controller;

import com.example.online_course.dto.request.CategoryRequest;
import com.example.online_course.dto.response.CategoryResponse;
import com.example.online_course.entity.Category;
import com.example.online_course.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/create")
    public CategoryResponse createCategory(@ModelAttribute CategoryRequest categoryRequest, @RequestParam("file") MultipartFile file)
        throws IOException {

        return categoryService.createCategory(categoryRequest, file);
    }
    }


