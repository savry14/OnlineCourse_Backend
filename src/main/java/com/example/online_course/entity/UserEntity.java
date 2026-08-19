package com.example.online_course.entity;

import com.example.online_course.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isActive = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private OtpEntity otp;
}