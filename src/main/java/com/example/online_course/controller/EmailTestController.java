//package com.example.online_course.controller;
//
//import com.example.online_course.service.EmailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/test-email")
//@RequiredArgsConstructor
//public class EmailTestController {
//
//    private final EmailService emailService;
//
//    @PostMapping
//    public ResponseEntity<String> testEmail(
//            @RequestParam String email
//    ) {
//
//        emailService.sendOtp(email, "123456");
//
//        return ResponseEntity.ok(
//                "Email sent successfully"
//        );
//    }
//}