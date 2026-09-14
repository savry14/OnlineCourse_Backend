package com.example.online_course.controller;

import com.example.online_course.dto.request.SectionOcrTextRequest;
import com.example.online_course.dto.response.SectionOcrTextResponse;
import com.example.online_course.service.SectionOcrTextService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections/{sectionId}/ocr-text")
@RequiredArgsConstructor
public class SectionOcrTextController {
    private final SectionOcrTextService sectionOcrTextService;

    @PostMapping
    public ResponseEntity<SectionOcrTextResponse> create(
            @PathVariable Long sectionId,
            @Valid @RequestBody SectionOcrTextRequest request) {
        request.setSectionId(sectionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(sectionOcrTextService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<SectionOcrTextResponse>> getAll(@PathVariable Long sectionId) {
        return ResponseEntity.ok(sectionOcrTextService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionOcrTextResponse> getById(
            @PathVariable Long sectionId,
            @PathVariable Long id) {
        return ResponseEntity.ok(sectionOcrTextService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectionOcrTextResponse> update(
            @PathVariable Long sectionId,
            @PathVariable Long id,
            @Valid @RequestBody SectionOcrTextRequest request) {
        request.setSectionId(sectionId);
        return ResponseEntity.ok(sectionOcrTextService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long sectionId,
            @PathVariable Long id) {
        sectionOcrTextService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
