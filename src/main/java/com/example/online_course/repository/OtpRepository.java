package com.example.online_course.repository;

import com.example.online_course.entity.OtpEntity;
import com.example.online_course.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Long, OtpEntity> {
    Optional<OtpEntity> findByUser(UserEntity user);
}
