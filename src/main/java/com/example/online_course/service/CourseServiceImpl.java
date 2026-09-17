package com.example.online_course.service;

import com.example.online_course.dto.request.CourseRequest;
import com.example.online_course.dto.response.CourseResponse;
import com.example.online_course.entity.CourseEntity;
import com.example.online_course.exception.ResourceNotFoundException;
import com.example.online_course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Value("${app.files.base-url}")
    private String filesBaseUrl;

    @Override
    public CourseResponse create(CourseRequest request) {
        CourseEntity course = CourseEntity.builder()
                .title(request.getTitle())
                .slug(request.getSlug())
                .category(request.getCategory())
                .badgeLabel(request.getBadgeLabel())
                .description(request.getDescription())
                .coverImage(saveCoverImage(request.getCoverImage()))
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
        if (request.getCoverImage() != null && !request.getCoverImage().isEmpty()) {
            course.setCoverImage(saveCoverImage(request.getCoverImage()));
        }
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
                .coverImage(toCoverImageUrl(course.getCoverImage()))
                .price(course.getPrice())
                .accessType(course.getAccessType())
                .format(course.getFormat())
                .pacing(course.getPacing())
                .isPublished(course.getIsPublished())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }

    private String toCoverImageUrl(String filename) {
        if (filename == null || filename.isBlank()) {
            return null;
        }
        return filesBaseUrl.replaceAll("/+$", "") + "/" + filename;
    }

    private String saveCoverImage(MultipartFile coverImage) {
        if (coverImage == null || coverImage.isEmpty()) {
            return null;
        }

        String originalFilename = coverImage.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.lastIndexOf('.') >= 0
                ? originalFilename.substring(originalFilename.lastIndexOf('.'))
                : "";
        String filename = UUID.randomUUID() + extension;
        Path uploadDirectory = Path.of("uploads", "courses").toAbsolutePath().normalize();

        try {
            Files.createDirectories(uploadDirectory);
            Files.copy(coverImage.getInputStream(), uploadDirectory.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not store course cover image", exception);
        }
    }
}
