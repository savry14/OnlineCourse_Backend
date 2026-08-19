package com.example.online_course.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseRequest {
    @NotBlank(message = "title is required")
    private String title;

    private String description;

    private String thumbnail;

    @NotNull(message = "price is required")
    private BigDecimal price;

    private Long categoryId;

    @NotNull(message = "userId is required")
    private Long userId;
}
