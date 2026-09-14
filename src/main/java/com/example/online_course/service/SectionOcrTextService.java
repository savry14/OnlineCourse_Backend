package com.example.online_course.service;

import com.example.online_course.dto.request.SectionOcrTextRequest;
import com.example.online_course.dto.response.SectionOcrTextResponse;

import java.util.List;

public interface SectionOcrTextService {
    SectionOcrTextResponse create(SectionOcrTextRequest request);
    SectionOcrTextResponse getById(Long id);
    List<SectionOcrTextResponse> getAll();
    SectionOcrTextResponse update(Long id, SectionOcrTextRequest request);
    void delete(Long id);
}
