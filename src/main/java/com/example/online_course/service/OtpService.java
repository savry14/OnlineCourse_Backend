package com.example.online_course.service;

import com.example.online_course.repository.OtpRepository;
import com.example.online_course.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository repository;
    private final UserRepository userRepository;

}
