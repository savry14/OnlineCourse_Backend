package com.example.online_course.service;

import com.example.online_course.dto.request.CategoryRequest;
import com.example.online_course.dto.response.CategoryResponse;
import com.example.online_course.entity.Category;
import com.example.online_course.exception.NotFoundException;
import com.example.online_course.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
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
//    private final UserRepository userRepository;
    @Override
    public CategoryResponse createCategory(
            CategoryRequest categoryRequest,
            MultipartFile file
    ) throws IOException {

//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        String email = authentication.name();

//      User user = userRepository.findByEmail(email)
//                .orElseThrow(() ->
//                        new NotFoundException("User not found!"));

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required!");
        }
        String filename = file.getOriginalFilename();

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Invalid file name!");
        }
        String fileUrl =
                UUID.randomUUID() + "_" +
                        Paths.get(filename).getFileName();
        Path path = Paths.get("koca");
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Files.copy(
                file.getInputStream(),
                path.resolve(fileUrl)
        );
        String imageUrl =
                "http://localhost:8080/Moko/" + fileUrl;

        Category category = Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .categoryEmail(categoryRequest.getCategoryEmail())
                .categoryImage(imageUrl)
//                .user(user)
                .build();
        categoryRepository.save(category);
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryImage(category.getCategoryImage())
                .categoryName(category.getCategoryName())
//                .userId(category.getCategoryId())
                .build();
    }
    public List<CategoryResponse> getCategoryById() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category category : categories) {
            CategoryResponse categoryResponse =
                    CategoryResponse.builder()
                            .categoryId(category.getCategoryId())
                            .categoryEmail(category.getCategoryEmail())
                            .categoryName(category.getCategoryName())
                            .categoryImage(category.getCategoryImage())
                            .categoryId(category.getCategoryId())
                            .build();
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }
    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Category not found with id: " + id
                        ));
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryEmail(category.getCategoryEmail())
                .categoryName(category.getCategoryName())
                .categoryImage(category.getCategoryImage())
//                .userId(category.getCategoryId())
                .build();
    }
}