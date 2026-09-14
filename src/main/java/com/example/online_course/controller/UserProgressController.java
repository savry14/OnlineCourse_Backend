package com.example.online_course.controller;

import com.example.online_course.dto.request.UserProgressRequest;
import com.example.online_course.dto.response.UserProgressResponse;
import com.example.online_course.service.UserProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/progress")
@RequiredArgsConstructor
public class UserProgressController {
    private final UserProgressService userProgressService;

    // PUT = upsert (idempotent) — was POST /progress/upsert
    @PutMapping
    public ResponseEntity<UserProgressResponse> upsert(
            @PathVariable Long userId,
            @Valid @RequestBody UserProgressRequest request) {
        request.setUserId(userId);
        return ResponseEntity.ok(userProgressService.upsert(request));
    }

    @GetMapping
    public ResponseEntity<List<UserProgressResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userProgressService.getByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long userId,
            @PathVariable Long id) {
        userProgressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
