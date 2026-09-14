package com.example.online_course.service;

import com.example.online_course.dto.request.CourseRequest;
import com.example.online_course.dto.response.CourseResponse;
import com.example.online_course.entity.CourseEntity;
import com.example.online_course.exception.ResourceNotFoundException;
import com.example.online_course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public CourseResponse create(CourseRequest request) {
        CourseEntity course = CourseEntity.builder()
                .title(request.getTitle())
                .slug(request.getSlug())
                .category(request.getCategory())
                .badgeLabel(request.getBadgeLabel())
                .description(request.getDescription())
                .coverImageUrl(request.getCoverImageUrl())
                .price(request.getPrice())
                .accessType(request.getAccessType())
                .format(request.getFormat())
                .pacing(request.getPacing())
                .isPublished(request.getIsPublished())
                .build();

        CourseEntity saved = courseRepository.save(course);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse getById(Long id) {
        CourseEntity course = findCourseOrThrow(id);
        return toResponse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CourseResponse update(Long id, CourseRequest request) {
        CourseEntity course = findCourseOrThrow(id);

        course.setTitle(request.getTitle());
        course.setSlug(request.getSlug());
        course.setCategory(request.getCategory());
        course.setBadgeLabel(request.getBadgeLabel());
        course.setDescription(request.getDescription());
        course.setCoverImageUrl(request.getCoverImageUrl());
        course.setPrice(request.getPrice());
        course.setAccessType(request.getAccessType());
        course.setFormat(request.getFormat());
        course.setPacing(request.getPacing());
        course.setIsPublished(request.getIsPublished());

        CourseEntity updated = courseRepository.save(course);
        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        CourseEntity course = findCourseOrThrow(id);
        courseRepository.delete(course);
    }

    // ---- helpers ----

    private CourseEntity findCourseOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }


    private CourseResponse toResponse(CourseEntity course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .slug(course.getSlug())
                .category(course.getCategory())
                .badgeLabel(course.getBadgeLabel())
                .description(course.getDescription())
                .coverImageUrl(course.getCoverImageUrl())
                .price(course.getPrice())
                .accessType(course.getAccessType())
                .format(course.getFormat())
                .pacing(course.getPacing())
                .isPublished(course.getIsPublished())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}
