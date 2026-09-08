package com.example.online_course.service;

import com.example.online_course.dto.request.ChangePasswordRequest;
import com.example.online_course.dto.request.UserRequest;
import com.example.online_course.dto.response.UserResponse;
import com.example.online_course.entity.UserEntity;
import com.example.online_course.exception.NotFoundException;
import com.example.online_course.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getMe(String email) {
        UserEntity user = findByEmail(email);
        return toResponse(user);
    }

    @Override
    public UserResponse updateProfile(String email, UserRequest request) {
        UserEntity user = findByEmail(email);
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }
        return toResponse(userRepository.save(user));
    }

    @Override
    public void changePassword(String email, ChangePasswordRequest request) {
        UserEntity user = findByEmail(email);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordChangedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        return toResponse(findById(id));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // helpers

    private UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    private UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    private UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .isVerified(user.getIsVerified())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .passwordChangedAt(user.getPasswordChangedAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}