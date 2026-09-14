package com.example.online_course.service;

import com.example.online_course.dto.request.SectionRequest;
import com.example.online_course.dto.response.SectionResponse;
import com.example.online_course.entity.CourseEntity;
import com.example.online_course.entity.SectionEntity;
import com.example.online_course.exception.ResourceNotFoundException;
import com.example.online_course.repository.CourseRepository;
import com.example.online_course.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;

    @Override
    public SectionResponse create(SectionRequest request) {
        CourseEntity course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + request.getCourseId()));
        SectionEntity section = buildSection(new SectionEntity(), course, request);
        return toResponse(sectionRepository.save(section));
    }

    @Override
    @Transactional(readOnly = true)
    public SectionResponse getById(Long id) {
        return toResponse(findSectionOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SectionResponse> getAll() {
        return sectionRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SectionResponse> getByCourseId(Long courseId) {
        return sectionRepository.findByCourse_IdOrderByPositionAsc(courseId).stream().map(this::toResponse).toList();
    }

    @Override
    public SectionResponse update(Long id, SectionRequest request) {
        SectionEntity section = findSectionOrThrow(id);
        CourseEntity course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + request.getCourseId()));
        return toResponse(sectionRepository.save(buildSection(section, course, request)));
    }

    @Override
    public void delete(Long id) {
        sectionRepository.delete(findSectionOrThrow(id));
    }

    private SectionEntity buildSection(SectionEntity section, CourseEntity course, SectionRequest request) {
        section.setCourse(course);
        section.setTitle(request.getTitle());
        section.setPosition(request.getPosition());
        section.setHasDocument(request.getHasDocument());
        section.setBody(request.getBody());
        section.setFileUrl(request.getFileUrl());
        section.setFileType(request.getFileType());
        section.setOcrStatus(request.getOcrStatus());
        section.setHasVideo(request.getHasVideo());
        section.setVideoUrl(request.getVideoUrl());
        section.setVideoDurationSec(request.getVideoDurationSec());
        section.setHasResources(request.getHasResources());
        section.setReadingMode(request.getReadingMode());
        section.setIsPreview(request.getIsPreview());
        return section;
    }

    private SectionEntity findSectionOrThrow(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + id));
    }

    private SectionResponse toResponse(SectionEntity section) {
        return SectionResponse.builder()
                .id(section.getId())
                .courseId(section.getCourse().getId())
                .title(section.getTitle())
                .position(section.getPosition())
                .hasDocument(section.getHasDocument())
                .body(section.getBody())
                .fileUrl(section.getFileUrl())
                .fileType(section.getFileType())
                .ocrStatus(section.getOcrStatus())
                .hasVideo(section.getHasVideo())
                .videoUrl(section.getVideoUrl())
                .videoDurationSec(section.getVideoDurationSec())
                .hasResources(section.getHasResources())
                .readingMode(section.getReadingMode())
                .isPreview(section.getIsPreview())
                .createdAt(section.getCreatedAt())
                .updatedAt(section.getUpdatedAt())
                .build();
    }
}
