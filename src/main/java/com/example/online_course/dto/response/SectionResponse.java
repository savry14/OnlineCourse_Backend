package com.example.online_course.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionResponse {
    private Long id;
    private Long courseId;
    private String title;
    private Integer position;
    private Boolean hasDocument;
    private String body;
    private String fileUrl;
    private String fileType;
    private String ocrStatus;
    private Boolean hasVideo;
    private String videoUrl;
    private Integer videoDurationSec;
    private Boolean hasResources;
    private String readingMode;
    private Boolean isPreview;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
