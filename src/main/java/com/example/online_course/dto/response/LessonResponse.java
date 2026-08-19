package com.example.online_course.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonResponse {
    private Long id;
    private String title;
    private String videoUrl;
    private String content;
    private Long courseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
