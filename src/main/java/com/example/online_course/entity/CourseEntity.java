package com.example.online_course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, unique = true, length = 255)
    private String slug;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(name = "badge_label", length = 100)
    private String badgeLabel;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "access_type", nullable = false, length = 50)
    private String accessType = "free_forever";

    @Column(length = 100)
    private String format;

    @Column(length = 100)
    private String pacing;

    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = Boolean.FALSE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private java.util.List<SectionEntity> sections = new java.util.ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.accessType == null) {
            this.accessType = "free_forever";
        }
        if (this.isPublished == null) {
            this.isPublished = Boolean.FALSE;
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
