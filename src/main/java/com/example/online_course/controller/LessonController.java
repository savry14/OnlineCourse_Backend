package com.example.online_course.controller;

import com.example.online_course.dto.request.LessonRequest;
import com.example.online_course.dto.response.LessonResponse;
import com.example.online_course.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    // POST /api/lessons
    @PostMapping
    public ResponseEntity<LessonResponse> create(@Valid @RequestBody LessonRequest request) {
        LessonResponse response = lessonService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/lessons
    @GetMapping
    public ResponseEntity<List<LessonResponse>> getAll() {
        return ResponseEntity.ok(lessonService.getAll());
    }

    // GET /api/lessons/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LessonResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getById(id));
    }

    // GET /api/lessons/by-course/{courseId}
    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<LessonResponse>> getByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getByCourseId(courseId));
    }

    // PUT /api/lessons/{id}
    @PutMapping("/{id}")
    public ResponseEntity<LessonResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody LessonRequest request) {
        return ResponseEntity.ok(lessonService.update(id, request));
    }

    // DELETE /api/lessons/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
