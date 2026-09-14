package com.example.online_course.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SectionRequest {
    @NotNull(message = "courseId is required")
    private Long courseId;

    @NotBlank(message = "title is required")
    private String title;

    @NotNull(message = "position is required")
    private Integer position;

    private Boolean hasDocument = Boolean.FALSE;
    private String body;
    private String fileUrl;
    private String fileType;
    private String ocrStatus = "not_applicable";
    private Boolean hasVideo = Boolean.FALSE;
    private String videoUrl;
    private Integer videoDurationSec;
    private Boolean hasResources = Boolean.FALSE;
    private String readingMode = "self_paced";
    private Boolean isPreview = Boolean.FALSE;
}
