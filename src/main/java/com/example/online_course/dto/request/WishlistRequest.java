package com.example.online_course.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WishlistRequest {
    @NotNull(message = "userId is required")
    private Long userId;
    @NotNull(message = "courseId is required")
    private Long courseId;
}
