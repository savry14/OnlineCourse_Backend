package com.example.online_course.dto.request;

import com.example.online_course.entity.UserEntity;
import com.example.online_course.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private String categoryName;
}
