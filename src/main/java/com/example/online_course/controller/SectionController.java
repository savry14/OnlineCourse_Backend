package com.example.online_course.controller;

import com.example.online_course.dto.request.SectionRequest;
import com.example.online_course.dto.response.SectionResponse;
import com.example.online_course.service.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/section")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @PostMapping("/create")
    public ResponseEntity<SectionResponse> create(@Valid @RequestBody SectionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectionService.create(request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SectionResponse>> getAll() {
        return ResponseEntity.ok(sectionService.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<SectionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sectionService.getById(id));
    }

    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<SectionResponse>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(sectionService.getByCourseId(courseId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SectionResponse> update(@PathVariable Long id, @Valid @RequestBody SectionRequest request) {
        return ResponseEntity.ok(sectionService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sectionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
