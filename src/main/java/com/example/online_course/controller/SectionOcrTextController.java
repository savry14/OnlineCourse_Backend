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
@RequestMapping("/api/section-ocr")
@RequiredArgsConstructor
public class SectionOcrTextController {
    private final SectionOcrTextService sectionOcrTextService;

    @PostMapping("/create")
    public ResponseEntity<SectionOcrTextResponse> create(@Valid @RequestBody SectionOcrTextRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectionOcrTextService.create(request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SectionOcrTextResponse>> getAll() {
        return ResponseEntity.ok(sectionOcrTextService.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<SectionOcrTextResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sectionOcrTextService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SectionOcrTextResponse> update(@PathVariable Long id, @Valid @RequestBody SectionOcrTextRequest request) {
        return ResponseEntity.ok(sectionOcrTextService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sectionOcrTextService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
