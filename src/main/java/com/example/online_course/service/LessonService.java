package com.example.online_course.service;

import com.example.online_course.dto.request.LessonRequest;
import com.example.online_course.dto.response.LessonResponse;

import java.util.List;

public interface LessonService {
    LessonResponse create(LessonRequest request);
    LessonResponse getById(Long id);
    List<LessonResponse> getAll();
    List<LessonResponse> getByCourseId(Long courseId);
    LessonResponse update(Long id, LessonRequest request);
    void delete(Long id);
}
