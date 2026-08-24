package com.example.online_course.controller;

import com.example.online_course.dto.request.LoginRequest;
import com.example.online_course.dto.request.RegisterRequest;
import com.example.online_course.dto.response.ApiResponse;
import com.example.online_course.dto.response.AuthResponse;
import com.example.online_course.dto.response.LoginResponse;
import com.example.online_course.service.AuthService;
import com.example.online_course.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/createAcc")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return new ApiResponse<>("Register successfully", 200, authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return new ApiResponse<>("Login successfully",200,authService.login(loginRequest));
    }

    @PostMapping("/test-email")
    public ApiResponse<String> testEmail(
            @RequestParam String email
    ) {

        emailService.sendOtp(email, "123456");

        return new ApiResponse<>(
                "Email sent successfully",
                200,
                "OTP sent to " + email
        );
    }

}
