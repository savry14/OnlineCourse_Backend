package com.example.online_course.controller;

import com.example.online_course.dto.request.CourseRequest;
import com.example.online_course.dto.response.CourseResponse;
import com.example.online_course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    // POST /api/course/create
    @PostMapping("/create")
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseRequest request) {
        CourseResponse response = courseService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/course/getAll
    @GetMapping("/getAll")
    public ResponseEntity<List<CourseResponse>> getAll() {
        return ResponseEntity.ok(courseService.getAll());
    }

    // GET /api/course/getById/{id}
    @GetMapping("/getById/{id}")
    public ResponseEntity<CourseResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    // PUT /api/course/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<CourseResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.update(id, request));
    }

    // DELETE /api/course/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
