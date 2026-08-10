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
@Table(name = "tbl_users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isActive = false;

//    @OneToMany(mappedBy = "user")
//    private List<CategoryEntity> categories;
//    @OneToMany(mappedBy = "user")
//    private List<CourseEntity> courses;
//    @OneToMany(mappedBy = "user")
//    private List<LessonEntity> lessons;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private OtpEntity otp;


}
