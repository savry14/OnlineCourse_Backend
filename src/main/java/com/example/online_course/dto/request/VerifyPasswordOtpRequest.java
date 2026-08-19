package com.example.online_course.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyPasswordOtpRequest {
        private String email;
        private String otp;
}
