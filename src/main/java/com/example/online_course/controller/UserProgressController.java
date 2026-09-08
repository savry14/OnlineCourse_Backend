package com.example.online_course.controller;

import com.example.online_course.dto.request.UserProgressRequest;
import com.example.online_course.dto.response.UserProgressResponse;
import com.example.online_course.service.UserProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class UserProgressController {
    private final UserProgressService userProgressService;

    @PostMapping("/upsert")
    public ResponseEntity<UserProgressResponse> upsert(@Valid @RequestBody UserProgressRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userProgressService.upsert(request));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<UserProgressResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userProgressService.getByUserId(userId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userProgressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
