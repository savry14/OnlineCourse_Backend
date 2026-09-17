package com.example.online_course.service;

import com.example.online_course.dto.request.LoginRequest;
import com.example.online_course.dto.request.RegisterRequest;
import com.example.online_course.dto.response.AuthResponse;
import com.example.online_course.dto.response.LoginResponse;
import com.example.online_course.entity.UserEntity;
import com.example.online_course.enums.Role;
import com.example.online_course.exception.EmailAlreadyExists;
import com.example.online_course.exception.EmailAndPasswordAreNotMatch;
import com.example.online_course.exception.NotFoundException;
import com.example.online_course.exception.AccountSuspendedException;
import com.example.online_course.repository.UserRepository;
import com.example.online_course.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest registerRequest){
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new EmailAlreadyExists("Email already exists");
        }
        String encodePassword = passwordEncoder.encode(registerRequest.getPassword());
        Role role = registerRequest.getRole() != null ? registerRequest.getRole() : Role.STUDENT;
        UserEntity userEntity = UserEntity.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(encodePassword)
                .role(role)
                .build();
        userEntity=userRepository.save(userEntity);
        return AuthResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }
    @Override
    public LoginResponse login(LoginRequest loginRequest){
        UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new NotFoundException("Email not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(),userEntity.getPassword())){
            throw new EmailAndPasswordAreNotMatch("Email and Password are not match.");
        }
        if (Boolean.TRUE.equals(userEntity.getIsSuspended())) {
            throw new AccountSuspendedException();
        }
        String token = jwtService.generateToken(userEntity);
        return LoginResponse.builder()
                .token(token)
                .authResponse(
                        AuthResponse.builder()
                                .id(userEntity.getId())
                                .name(userEntity.getName())
                                .email(userEntity.getEmail())
                                .role(userEntity.getRole())
                                .build()
                )
                .build();
    }
}
