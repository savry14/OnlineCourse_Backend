package com.example.online_course.exception;

public class EmailAndPasswordAreNotMatch extends RuntimeException {
    public EmailAndPasswordAreNotMatch(String message) {
        super(message);
    }
}
