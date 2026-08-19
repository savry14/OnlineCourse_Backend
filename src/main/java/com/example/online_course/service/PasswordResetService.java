package com.example.online_course.service;

public interface PasswordResetService {
    void generateOtp(String email);
    void verifyOtp(String email, String otp);
    void resetPassword(String email, String newPassword);
}
