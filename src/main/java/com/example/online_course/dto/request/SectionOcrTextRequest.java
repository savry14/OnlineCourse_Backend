package com.example.online_course.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SectionOcrTextRequest {
    // Populated from URL path variable — not sent in request body
    private Long sectionId;

    @NotBlank(message = "sourceFile is required")
    private String sourceFile;

    @NotBlank(message = "extractedText is required")
    private String extractedText;

    private BigDecimal confidence;
    private String language = "en";
}
