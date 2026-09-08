package com.example.online_course.repository;

import com.example.online_course.entity.CourseEntity;
import com.example.online_course.entity.UserEntity;
import com.example.online_course.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
    Optional<WishlistEntity> findByUserAndCourse(UserEntity user, CourseEntity course);
    List<WishlistEntity> findByUser_Id(Long userId);
}
