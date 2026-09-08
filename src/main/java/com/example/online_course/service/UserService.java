package com.example.online_course.service;

import com.example.online_course.dto.request.ChangePasswordRequest;
import com.example.online_course.dto.request.UserRequest;
import com.example.online_course.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getMe(String email);
    UserResponse updateProfile(String email, UserRequest request);
    void changePassword(String email, ChangePasswordRequest request);
    List<UserResponse> getAll();
    UserResponse getById(Long id);
    void deleteUser(Long id);
}
