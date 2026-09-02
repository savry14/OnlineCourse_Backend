package com.example.online_course.service;

import com.example.online_course.dto.request.LessonRequest;
import com.example.online_course.dto.response.LessonResponse;
import com.example.online_course.entity.CourseEntity;
import com.example.online_course.entity.LessonEntity;
import com.example.online_course.exception.ResourceNotFoundException;
import com.example.online_course.repository.CourseRepository;
import com.example.online_course.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonServiceImpl implements LessonService{
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public LessonResponse create(LessonRequest request) {
        CourseEntity course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + request.getCourseId()));

        LessonEntity lesson = LessonEntity.builder()
                .title(request.getTitle())
                .videoUrl(request.getVideoUrl())
                .content(request.getContent())
                .course(course)
                .build();

        LessonEntity saved = lessonRepository.save(lesson);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public LessonResponse getById(Long id) {
        LessonEntity lesson = findLessonOrThrow(id);
        return toResponse(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponse> getAll() {
        return lessonRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponse> getByCourseId(Long courseId) {
        return lessonRepository.findByCourse_Id(courseId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LessonResponse update(Long id, LessonRequest request) {
        LessonEntity lesson = findLessonOrThrow(id);

        // If courseId changed, re-resolve the Course relation
        if (!lesson.getCourse().getId().equals(request.getCourseId())) {
            CourseEntity course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Course not found with id: " + request.getCourseId()));
            lesson.setCourse(course);
        }

        lesson.setTitle(request.getTitle());
        lesson.setVideoUrl(request.getVideoUrl());
        lesson.setContent(request.getContent());

        LessonEntity updated = lessonRepository.save(lesson);
        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        LessonEntity lesson = findLessonOrThrow(id);
        lessonRepository.delete(lesson);
    }

    // ---- helpers ----

    private LessonEntity findLessonOrThrow(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
    }

    private LessonResponse toResponse(LessonEntity lesson) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .videoUrl(lesson.getVideoUrl())
                .content(lesson.getContent())
                .courseId(lesson.getCourse().getId())
                .createdAt(lesson.getCreatedAt())
                .updatedAt(lesson.getUpdatedAt())
                .build();
    }
}
