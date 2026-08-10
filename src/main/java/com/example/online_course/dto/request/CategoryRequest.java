package com.example.online_course.dto.request;

import com.example.online_course.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private Long categoryId;
    private String categoryName;
    private String categoryEmail;
    private String categoryImage;
    private Role role;
    private Long userId;
}
