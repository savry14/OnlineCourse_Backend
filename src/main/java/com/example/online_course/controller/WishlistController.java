package com.example.online_course.controller;

import com.example.online_course.dto.request.WishlistRequest;
import com.example.online_course.dto.response.WishlistResponse;
import com.example.online_course.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/me/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<WishlistResponse> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody WishlistRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<WishlistResponse>> getMyWishlist(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(wishlistService.getByUsername(userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        wishlistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
