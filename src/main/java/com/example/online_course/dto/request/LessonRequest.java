package com.example.online_course.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LessonRequest {
    @NotBlank(message = "title is required")
    private String title;

    private String videoUrl;

    private String content;

    @NotNull(message = "courseId is required")
    private Long courseId;
}
