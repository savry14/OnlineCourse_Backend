package com.example.online_course.service;

import com.example.online_course.dto.request.UserProgressRequest;
import com.example.online_course.dto.response.UserProgressResponse;

import java.util.List;

public interface UserProgressService {
    UserProgressResponse upsert(UserProgressRequest request);
    List<UserProgressResponse> getByUserId(Long userId);
    void delete(Long id);
}
