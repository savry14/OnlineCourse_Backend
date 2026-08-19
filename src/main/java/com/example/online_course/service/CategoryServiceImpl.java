package com.example.online_course.service;

import com.example.online_course.dto.request.CategoryRequest;
import com.example.online_course.dto.response.CategoryResponse;
import com.example.online_course.entity.CategoryEntity;
import com.example.online_course.entity.UserEntity;
import com.example.online_course.exception.NotFoundException;
import com.example.online_course.repository.CategoryRepository;

import com.example.online_course.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundException("User is not authenticated!");
        }
        String email = authentication.getName();
        UserEntity admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        CategoryEntity category = CategoryEntity.builder()
                .categoryName(categoryRequest.getCategoryName())
                .createdBy(admin)
                .updatedBy(admin)
                .build();
        categoryRepository.save(category);
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .createdBy(category.getCreatedBy().getId())
                .updatedBy(category.getUpdatedBy().getId())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> CategoryResponse.builder()
                        .categoryId(category.getCategoryId())
                        .categoryName(category.getCategoryName())
                        .createdBy(
                                category.getCreatedBy() != null ? category.getCreatedBy().getId() : null
                        )
                        .updatedBy(
                                category.getUpdatedBy() != null ? category.getUpdatedBy().getId() : null
                        )
                        .createdAt(category.getCreatedAt())
                        .updatedAt(category.getUpdatedAt())
                        .build()
                )
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Category not found!")
                );

        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .createdBy(
                        category.getCreatedBy() != null ? category.getCreatedBy().getId() : null
                )
                .updatedBy(
                        category.getUpdatedBy() != null ? category.getUpdatedBy().getId() : null
                )
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }


    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundException("User is not authenticated!");
        }
        String email = authentication.getName();
        UserEntity admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Admin not found!"));
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setUpdatedBy(admin);
        categoryRepository.save(category);
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .createdBy(category.getCreatedBy().getId())
                .updatedBy(category.getUpdatedBy().getId())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    @Override
    public void deleteCategory(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundException("User is not authenticated!");
        }
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        categoryRepository.delete(category);
    }
}