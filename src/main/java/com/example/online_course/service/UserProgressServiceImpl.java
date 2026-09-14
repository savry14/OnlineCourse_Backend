package com.example.online_course.service;

import com.example.online_course.dto.request.UserProgressRequest;
import com.example.online_course.dto.response.UserProgressResponse;
import com.example.online_course.entity.SectionEntity;
import com.example.online_course.entity.UserEntity;
import com.example.online_course.entity.UserProgressEntity;
import com.example.online_course.exception.ResourceNotFoundException;
import com.example.online_course.repository.SectionRepository;
import com.example.online_course.repository.UserProgressRepository;
import com.example.online_course.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProgressServiceImpl implements UserProgressService {
    private final UserProgressRepository userProgressRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;

    @Override
    public UserProgressResponse upsert(UserProgressRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));
        SectionEntity section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + request.getSectionId()));
        UserProgressEntity progress = userProgressRepository.findByUserAndSection(user, section)
                .orElseGet(() -> UserProgressEntity.builder().user(user).section(section).build());
        progress.setIsCompleted(request.getIsCompleted());
        progress.setWatchedSeconds(request.getWatchedSeconds());
        if (Boolean.TRUE.equals(request.getIsCompleted()) && progress.getCompletedAt() == null) {
            progress.setCompletedAt(LocalDateTime.now());
        }
        return toResponse(userProgressRepository.save(progress));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProgressResponse> getByUserId(Long userId) {
        return userProgressRepository.findByUser_Id(userId).stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Long id) {
        userProgressRepository.deleteById(id);
    }

    private UserProgressResponse toResponse(UserProgressEntity progress) {
        return UserProgressResponse.builder()
                .id(progress.getId())
                .userId(progress.getUser().getId())
                .sectionId(progress.getSection().getId())
                .isCompleted(progress.getIsCompleted())
                .watchedSeconds(progress.getWatchedSeconds())
                .completedAt(progress.getCompletedAt())
                .createdAt(progress.getCreatedAt())
                .updatedAt(progress.getUpdatedAt())
                .build();
    }
}
