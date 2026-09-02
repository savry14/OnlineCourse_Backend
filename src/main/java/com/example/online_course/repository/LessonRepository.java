package com.example.online_course.repository;

import com.example.online_course.entity.LessonEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    List<LessonEntity> findByCourse_Id(Long courseId);
}
