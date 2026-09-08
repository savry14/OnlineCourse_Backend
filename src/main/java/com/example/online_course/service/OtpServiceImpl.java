package com.example.online_course.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpServiceImpl implements OtpService {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generateOtp() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }
}
