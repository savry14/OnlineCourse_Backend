package com.example.online_course.controller;

import com.example.online_course.dto.request.ChangePasswordRequest;
import com.example.online_course.dto.request.UserRequest;
import com.example.online_course.dto.response.ApiResponse;
import com.example.online_course.dto.response.UserResponse;
import com.example.online_course.enums.Role;
import com.example.online_course.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ── Own profile (any authenticated user) ─────────────────────────────────

    @GetMapping("/me")
    public ApiResponse<UserResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        return new ApiResponse<>("Get profile successfully", 200,
                userService.getMe(userDetails.getUsername()));
    }

    @PutMapping("/me")
    public ApiResponse<UserResponse> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserRequest request) {
        return new ApiResponse<>("Update profile successfully", 200,
                userService.updateProfile(userDetails.getUsername(), request));
    }

    @PutMapping("/me/password")
    public ApiResponse<String> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChangePasswordRequest request) {
        userService.changePassword(userDetails.getUsername(), request);
        return new ApiResponse<>("Password changed successfully", 200, null);
    }

    // ── Admin endpoints ───────────────────────────────────────────────────────

    @GetMapping
    public ApiResponse<List<UserResponse>> getAll() {
        return new ApiResponse<>("Get all users successfully", 200, userService.getAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getById(@PathVariable Long id) {
        return new ApiResponse<>("Get user successfully", 200, userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ApiResponse<>("User deleted successfully", 200, null);
    }

    @PatchMapping("/{id}/role")
    public ApiResponse<UserResponse> changeRole(
            @PathVariable Long id,
            @RequestParam Role role) {
        return new ApiResponse<>("Role changed successfully", 200, userService.changeRole(id, role));
    }

    @PatchMapping("/{id}/suspend")
    public ApiResponse<UserResponse> suspendUser(@PathVariable Long id) {
        return new ApiResponse<>("User suspended successfully", 200, userService.suspendUser(id));
    }

    @PatchMapping("/{id}/unsuspend")
    public ApiResponse<UserResponse> unsuspendUser(@PathVariable Long id) {
        return new ApiResponse<>("User unsuspended successfully", 200, userService.unsuspendUser(id));
    }
}