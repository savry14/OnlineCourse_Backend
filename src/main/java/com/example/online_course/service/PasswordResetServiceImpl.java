package com.example.online_course.service;

import com.example.online_course.entity.PasswordResetEntity;
import com.example.online_course.entity.UserEntity;
import com.example.online_course.exception.NotFoundException;
import com.example.online_course.repository.PasswordResetOtpRepository;
import com.example.online_course.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService{
    private final UserRepository userRepository;
    private final PasswordResetOtpRepository passwordResetOtpRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final EmailService emailService;

    @Override
    public void generateOtp(String email){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("User with this email does not exist"));
        String otp = otpService.generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);
        PasswordResetEntity resetEntity = passwordResetOtpRepository
                .findByUserEntity(user)
                .orElse(PasswordResetEntity
                        .builder()
                        .userEntity(user)
                        .build()
                );
        resetEntity.setOtp(otp);
        resetEntity.setExpiryTime(expiryTime);
        resetEntity.setVerified(false);

        passwordResetOtpRepository.save(resetEntity);

        emailService.sendOtpEmail(user.getEmail(), otp);

        System.out.println("=================================");
        System.out.println("PASSWORD RESET OTP: " + otp);
        System.out.println("EXPIRES AT: " + expiryTime);
        System.out.println("=================================");
    }
    @Override
    public void verifyOtp(String email, String otp) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("User with this email does not exist")
                );

        PasswordResetEntity resetEntity = passwordResetOtpRepository
                .findByUserEntity(user)
                .orElseThrow(() ->
                        new NotFoundException("OTP was not requested")
                );

        // Check OTP
        if (!resetEntity.getOtp().equals(otp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        // Check expiry
        if (resetEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP has expired");
        }

        // OTP is correct
        resetEntity.setVerified(true);

        passwordResetOtpRepository.save(resetEntity);
    }

    @Override
    public void resetPassword(String email, String newPassword) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("User with this email does not exist")
                );

        PasswordResetEntity resetEntity = passwordResetOtpRepository
                .findByUserEntity(user)
                .orElseThrow(() ->
                        new NotFoundException("OTP was not requested")
                );

        // Make sure OTP was verified
        if (!resetEntity.isVerified()) {
            throw new IllegalArgumentException("Please verify OTP first");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetEntity.setVerified(false);
        resetEntity.setExpiryTime(LocalDateTime.now());

        passwordResetOtpRepository.save(resetEntity);
    }
}
