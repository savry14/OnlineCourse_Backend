package com.example.online_course.controller;

import com.example.online_course.dto.request.ForgetPasswordRequest;
import com.example.online_course.dto.request.ResetPasswordRequest;
import com.example.online_course.dto.request.VerifyPasswordOtpRequest;
import com.example.online_course.dto.response.ApiResponse;
import com.example.online_course.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/password")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forget")
    public ApiResponse<String> forgetPassword(@RequestBody ForgetPasswordRequest request) {
        passwordResetService.generateOtp(request.getEmail());
        return new ApiResponse<>("OTP generated successfully", 200, "OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestBody VerifyPasswordOtpRequest request) {
        passwordResetService.verifyOtp(request.getEmail(), request.getOtp());
        ApiResponse<String> response = new ApiResponse<>(
                "OTP verified successfully",
                200,
                "You can now reset your password"
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset")
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