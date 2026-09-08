package com.example.online_course.repository;

import com.example.online_course.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
    List<SectionEntity> findByCourse_IdOrderByPositionAsc(Long courseId);
}
