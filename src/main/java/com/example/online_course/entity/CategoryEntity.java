package com.example.online_course.entity;

import com.example.online_course.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_category")
public class CategoryEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long categoryId;
        private String categoryName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "created_by", nullable = false)
        private UserEntity createdBy;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "updated_by")
        private UserEntity updatedBy;

        @PrePersist
        protected void onCreate() {
            createdAt = LocalDateTime.now();
            updatedAt = LocalDateTime.now();
        }

        @PreUpdate
        protected void onUpdate() {
            updatedAt = LocalDateTime.now();
        }


    }
