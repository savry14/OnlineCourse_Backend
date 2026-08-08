package com.example.online_course.dto.response;

public class ApiResponse <T>{
    private String massage;
    private Integer status;
    private T data;
}
