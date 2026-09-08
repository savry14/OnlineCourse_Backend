package com.example.online_course.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionOcrTextResponse {
    private Long id;
    private Long sectionId;
    private String sourceFile;
    private String extractedText;
    private BigDecimal confidence;
    private String language;
    private LocalDateTime createdAt;
}
