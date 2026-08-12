

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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private String categoryEmail;
    private String categoryImage;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Role role;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }


}
