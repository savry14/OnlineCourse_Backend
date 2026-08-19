package com.example.online_course.service;

import com.example.online_course.dto.request.CourseRequest;
import com.example.online_course.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {
    CourseResponse create(CourseRequest request);
    CourseResponse getById(Long id);
    List<CourseResponse> getAll();
    CourseResponse update(Long id, CourseRequest request);
    void delete(Long id);
}
