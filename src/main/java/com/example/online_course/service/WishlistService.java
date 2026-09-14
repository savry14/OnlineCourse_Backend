package com.example.online_course.service;

import com.example.online_course.dto.request.WishlistRequest;
import com.example.online_course.dto.response.WishlistResponse;

import java.util.List;

public interface WishlistService {
    WishlistResponse create(WishlistRequest request);
    List<WishlistResponse> getByUserId(Long userId);
    List<WishlistResponse> getByUsername(String email);
    void delete(Long id);
}
