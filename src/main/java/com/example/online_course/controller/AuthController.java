package com.example.online_course.controller;

import com.example.online_course.dto.request.ForgetPasswordRequest;
import com.example.online_course.dto.request.LoginRequest;
import com.example.online_course.dto.request.RegisterRequest;
import com.example.online_course.dto.request.ResetPasswordRequest;
import com.example.online_course.dto.request.VerifyPasswordOtpRequest;
import com.example.online_course.dto.response.ApiResponse;
import com.example.online_course.dto.response.AuthResponse;
import com.example.online_course.dto.response.LoginResponse;
import com.example.online_course.service.AuthService;
import com.example.online_course.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    // ── Registration & Login ──────────────────────────────────────────────────

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        System.out.println("🔥 REGISTER CONTROLLER REACHED");
        return new ApiResponse<>("Register successfully", 200, authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ApiResponse<>("Login successfully", 200, authService.login(loginRequest));
    }

    // ── Password Reset Flow ───────────────────────────────────────────────────

    @PostMapping("/password/forgot")
    public ApiResponse<String> forgotPassword(@RequestBody ForgetPasswordRequest request) {
        passwordResetService.generateOtp(request.getEmail());
        return new ApiResponse<>("OTP generated successfully", 200, "OTP sent successfully");
    }

    @PostMapping("/password/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestBody VerifyPasswordOtpRequest request) {
        passwordResetService.verifyOtp(request.getEmail(), request.getOtp());
        ApiResponse<String> response = new ApiResponse<>(
                "OTP verified successfully",
                200,
                "You can now reset your password"
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password/reset")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.getEmail(), request.getNewPassword());
        ApiResponse<String> response = new ApiResponse<>(
                "Password reset successfully",
                200,
                "You can now login with your new password"
        );
        return ResponseEntity.ok(response);
    }
}
