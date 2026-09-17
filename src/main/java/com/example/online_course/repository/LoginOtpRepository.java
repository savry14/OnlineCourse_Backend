package com.example.online_course.repository;

import com.example.online_course.entity.LoginOtpEntity;
import com.example.online_course.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginOtpRepository extends JpaRepository<LoginOtpEntity, Long> {

    Optional<LoginOtpEntity> findByUser(UserEntity user);
    void deleteByUser(UserEntity user);
}
