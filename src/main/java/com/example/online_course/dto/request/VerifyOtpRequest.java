package com.example.online_course.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyOtpRequest {
    private String email;
    private String otp;
}
