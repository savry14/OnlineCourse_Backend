package com.example.online_course.exception;

public class AccountSuspendedException extends RuntimeException {
    public AccountSuspendedException() {
        super("This account has been suspended");
    }
}
