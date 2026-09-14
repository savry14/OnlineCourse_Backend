package com.example.online_course.repository;

import com.example.online_course.entity.SectionEntity;
import com.example.online_course.entity.SectionOcrTextEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionOcrTextRepository extends JpaRepository<SectionOcrTextEntity, Long> {
    Optional<SectionOcrTextEntity> findBySection(SectionEntity section);
}
