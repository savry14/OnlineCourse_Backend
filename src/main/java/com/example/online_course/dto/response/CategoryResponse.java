package com.example.online_course.dto.response;

import com.example.online_course.entity.Category;
import com.example.online_course.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private String categoryEmail;
    private String categoryImage;
    private Role role;
    private Long userId;
}
