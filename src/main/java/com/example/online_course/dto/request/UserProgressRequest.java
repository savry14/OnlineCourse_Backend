package com.example.online_course.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserProgressRequest {
    @NotNull(message = "userId is required")
    private Long userId;
    @NotNull(message = "sectionId is required")
    private Long sectionId;
    private Boolean isCompleted = Boolean.FALSE;
    private Integer watchedSeconds = 0;
}
