package com.example.online_course.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseRequest {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "slug is required")
    private String slug;

    @NotBlank(message = "category is required")
    private String category;

    private String badgeLabel;

    private String description;

    private String coverImageUrl;

    @NotNull(message = "price is required")
    private BigDecimal price;

    private String accessType = "free_forever";

    private String format;

    private String pacing;

    private Boolean isPublished = Boolean.FALSE;
}
