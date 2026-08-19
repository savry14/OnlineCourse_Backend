package com.example.online_course.repository;

import com.example.online_course.entity.PasswordResetEntity;
import com.example.online_course.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetEntity, Long> {

    Optional<PasswordResetEntity> findByUserEntity(UserEntity userEntity);
    Optional<PasswordResetEntity> findByUserEntityAndOtp(UserEntity userEntity, String otp);

}