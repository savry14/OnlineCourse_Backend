package com.example.online_course.controller;

import com.example.online_course.dto.request.WishlistRequest;
import com.example.online_course.dto.response.WishlistResponse;
import com.example.online_course.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/create")
    public ResponseEntity<WishlistResponse> create(@Valid @RequestBody WishlistRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistService.create(request));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<WishlistResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(wishlistService.getByUserId(userId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        wishlistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
