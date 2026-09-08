package com.example.online_course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_sections")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private Integer position;

    @Column(name = "has_document", nullable = false)
    @Builder.Default
    private Boolean hasDocument = Boolean.FALSE;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "file_url", length = 500)
    private String fileUrl;

    @Column(name = "file_type", length = 50)
    private String fileType;

    @Column(name = "ocr_status", length = 50)
    @Builder.Default
    private String ocrStatus = "not_applicable";

    @Column(name = "has_video", nullable = false)
    @Builder.Default
    private Boolean hasVideo = Boolean.FALSE;

    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @Column(name = "video_duration_sec")
    private Integer videoDurationSec;

    @Column(name = "has_resources", nullable = false)
    @Builder.Default
    private Boolean hasResources = Boolean.FALSE;

    @Column(name = "reading_mode", length = 50)
    @Builder.Default
    private String readingMode = "self_paced";

    @Column(name = "is_preview", nullable = false)
    @Builder.Default
    private Boolean isPreview = Boolean.FALSE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private SectionOcrTextEntity ocrText;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SectionResourceEntity> resources = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserProgressEntity> progressEntries = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
