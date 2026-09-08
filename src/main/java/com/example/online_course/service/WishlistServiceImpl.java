package com.example.online_course.service;

import com.example.online_course.dto.request.WishlistRequest;
import com.example.online_course.dto.response.WishlistResponse;
import com.example.online_course.entity.CourseEntity;
import com.example.online_course.entity.UserEntity;
import com.example.online_course.entity.WishlistEntity;
import com.example.online_course.exception.ResourceNotFoundException;
import com.example.online_course.repository.CourseRepository;
import com.example.online_course.repository.UserRepository;
import com.example.online_course.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public WishlistResponse create(WishlistRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));
        CourseEntity course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + request.getCourseId()));
        WishlistEntity wishlist = wishlistRepository.findByUserAndCourse(user, course)
                .orElseGet(() -> WishlistEntity.builder().user(user).course(course).build());
        return toResponse(wishlistRepository.save(wishlist));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WishlistResponse> getByUserId(Long userId) {
        return wishlistRepository.findByUser_Id(userId).stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        wishlistRepository.deleteById(id);
    }

    private WishlistResponse toResponse(WishlistEntity wishlist) {
        return WishlistResponse.builder()
                .id(wishlist.getId())
                .userId(wishlist.getUser().getId())
                .courseId(wishlist.getCourse().getId())
                .createdAt(wishlist.getCreatedAt())
                .build();
    }
}
