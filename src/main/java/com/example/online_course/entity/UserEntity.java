package com.example.online_course.entity;

import com.example.online_course.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "createdBy")
    private List<CategoryEntity> createdCategories;

    @OneToMany(mappedBy = "updatedBy")
    private List<CategoryEntity> updatedCategories;
//    @OneToMany(mappedBy = "user")
//    private List<CourseEntity> courses;
//    @OneToMany(mappedBy = "user")
//    private List<LessonEntity> lessons;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private OtpEntity otp;


}
