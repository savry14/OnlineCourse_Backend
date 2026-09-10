package com.example.online_course.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserProgressRequest {
    // Populated from URL path variable — not sent in request body
    private Long userId;
    @NotNull(message = "sectionId is required")
    private Long sectionId;
    private Boolean isCompleted = Boolean.FALSE;
    private Integer watchedSeconds = 0;
}
