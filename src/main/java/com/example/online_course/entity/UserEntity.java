package com.example.online_course.entity;

import com.example.online_course.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_verified", nullable = false)
    @Builder.Default
    private Boolean isVerified = Boolean.FALSE;

    @Column(name = "is_suspended", nullable = false)
    @Builder.Default
    private Boolean isSuspended = Boolean.FALSE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;

    @OneToMany(mappedBy = "createdBy")
    @Builder.Default
    private List<CategoryEntity> createdCategories = new ArrayList<>();

    @OneToMany(mappedBy = "updatedBy")
    @Builder.Default
    private List<CategoryEntity> updatedCategories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserProgressEntity> progressEntries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<WishlistEntity> wishlists = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.isVerified == null) {
            this.isVerified = Boolean.FALSE;
        }
        if (this.role == null) {
            this.role = Role.STUDENT;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
