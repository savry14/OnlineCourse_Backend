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
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
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
                .categoryImage(saveCategoryImage(categoryRequest.getCategoryImage()))
                .createdBy(admin)
                .updatedBy(admin)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        categoryRepository.save(category);
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryImage(category.getCategoryImage())
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
                        .categoryImage(category.getCategoryImage())
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
                .categoryImage(category.getCategoryImage())
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
        if (categoryRequest.getCategoryImage() != null && !categoryRequest.getCategoryImage().isEmpty()) {
            category.setCategoryImage(saveCategoryImage(categoryRequest.getCategoryImage()));
        }
        category.setUpdatedBy(admin);
        categoryRepository.save(category);
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .categoryImage(category.getCategoryImage())
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

    private String saveCategoryImage(MultipartFile categoryImage) {
        if (categoryImage == null || categoryImage.isEmpty()) {
            return null;
        }
        if (categoryImage.getContentType() == null || !categoryImage.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Category image must be an image file");
        }

        String originalFilename = categoryImage.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.lastIndexOf('.') >= 0
                ? originalFilename.substring(originalFilename.lastIndexOf('.'))
                : "";
        String filename = UUID.randomUUID() + extension;
        Path uploadDirectory = Path.of("uploads", "categories").toAbsolutePath().normalize();

        try {
            Files.createDirectories(uploadDirectory);
            Files.copy(categoryImage.getInputStream(), uploadDirectory.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not store category image", exception);
        }
    }
}
