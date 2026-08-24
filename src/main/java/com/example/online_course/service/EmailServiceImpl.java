package com.example.online_course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String to, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("TosRean Verification Code");

        message.setText(
                "Hello,\n\n" +
                        "Your TosRean verification code is:\n\n" +
                        otp + "\n\n" +
                        "This OTP will expire in 5 minutes.\n\n" +
                        "If you did not request this code, please ignore this email.\n\n" +
                        "TosRean\n" +
                        "Learn. Build. Grow."
        );

        mailSender.send(message);
    }
}
