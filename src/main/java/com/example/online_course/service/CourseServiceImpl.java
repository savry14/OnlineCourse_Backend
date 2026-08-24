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
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .price(request.getPrice())
                .categoryId(request.getCategoryId())
                .userId(request.getUserId())
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
        course.setDescription(request.getDescription());
        course.setThumbnail(request.getThumbnail());
        course.setPrice(request.getPrice());
        course.setCategoryId(request.getCategoryId());
        course.setUserId(request.getUserId());

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
                .description(course.getDescription())
                .thumbnail(course.getThumbnail())
                .price(course.getPrice())
                .categoryId(course.getCategoryId())
                .userId(course.getUserId())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}
