package com.example.online_course.repository;

import com.example.online_course.entity.SectionEntity;
import com.example.online_course.entity.UserEntity;
import com.example.online_course.entity.UserProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgressEntity, Long> {
    Optional<UserProgressEntity> findByUserAndSection(UserEntity user, SectionEntity section);
    List<UserProgressEntity> findByUser_Id(Long userId);
}
