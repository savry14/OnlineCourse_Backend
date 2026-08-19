package com.example.online_course.controller;

import com.example.online_course.dto.request.LoginRequest;
import com.example.online_course.dto.request.RegisterRequest;
import com.example.online_course.dto.response.ApiResponse;
import com.example.online_course.dto.response.AuthResponse;
import com.example.online_course.dto.response.LoginResponse;
import com.example.online_course.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/createAcc")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
       return new ApiResponse<>("Register successfully", 200, authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return new ApiResponse<>("Login successfully",200,authService.login(loginRequest));
    }

}
