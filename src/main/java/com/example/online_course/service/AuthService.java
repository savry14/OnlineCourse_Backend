package com.example.online_course.service;

import com.example.online_course.dto.request.LoginRequest;
import com.example.online_course.dto.request.RegisterRequest;
import com.example.online_course.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest
    );
}
