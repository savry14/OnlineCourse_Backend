package com.example.online_course.exception;

public class EmailAllreadyExists extends RuntimeException {
    public EmailAllreadyExists(String message) {
        super(message);
    }
}
