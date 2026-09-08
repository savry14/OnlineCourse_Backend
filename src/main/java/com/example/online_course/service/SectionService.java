package com.example.online_course.service;

import com.example.online_course.dto.request.SectionRequest;
import com.example.online_course.dto.response.SectionResponse;

import java.util.List;

public interface SectionService {
    SectionResponse create(SectionRequest request);
    SectionResponse getById(Long id);
    List<SectionResponse> getAll();
    List<SectionResponse> getByCourseId(Long courseId);
    SectionResponse update(Long id, SectionRequest request);
    void delete(Long id);
}
