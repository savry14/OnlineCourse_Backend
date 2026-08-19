package com.example.online_course.service;

import com.example.online_course.dto.request.LoginRequest;
import com.example.online_course.dto.request.RegisterRequest;
import com.example.online_course.dto.response.AuthResponse;
import com.example.online_course.dto.response.LoginResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}
