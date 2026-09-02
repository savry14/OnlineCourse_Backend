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
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    // POST /api/lesson/create
    @PostMapping("/create")
    public ResponseEntity<LessonResponse> create(@Valid @RequestBody LessonRequest request) {
        LessonResponse response = lessonService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/lesson/getAll
    @GetMapping("/getAll")
    public ResponseEntity<List<LessonResponse>> getAll() {
        return ResponseEntity.ok(lessonService.getAll());
    }

    // GET /api/lesson/getById/{id}
    @GetMapping("/getById/{id}")
    public ResponseEntity<LessonResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getById(id));
    }

    // GET /api/lesson/by-course/{courseId}
    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<LessonResponse>> getByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getByCourseId(courseId));
    }

    // PUT /api/lesson/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<LessonResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody LessonRequest request) {
        return ResponseEntity.ok(lessonService.update(id, request));
    }

    // DELETE /api/lesson/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
