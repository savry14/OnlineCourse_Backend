package com.example.online_course.dto.response;

import com.example.online_course.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Boolean isVerified;
    private String avatarUrl;
    private String bio;
    private LocalDateTime passwordChangedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
