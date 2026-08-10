package com.example.online_course.service;

import com.example.online_course.dto.request.RegisterRequest;
import com.example.online_course.dto.response.AuthResponse;
import com.example.online_course.entity.UserEntity;
import com.example.online_course.enums.Role;
import com.example.online_course.exception.EmailAlreadyExists;
import com.example.online_course.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Data
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest registerRequest){
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new EmailAlreadyExists("Email already exists");
        }
        String encodePassword = passwordEncoder.encode(registerRequest.getPassword());
        UserEntity userEntity = UserEntity.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(encodePassword)
                .createdAt(LocalDateTime.now())
                .role(Role.STUDENT)
                .isActive(false)
                .build();
        userEntity=userRepository.save(userEntity);
        return AuthResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .createdAt(userEntity.getCreatedAt())
                .role(userEntity.getRole())
                .build();



    }
}
