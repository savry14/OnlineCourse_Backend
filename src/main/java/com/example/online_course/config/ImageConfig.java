package com.example.online_course.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry){
        resourceHandlerRegistry.addResourceHandler("/api/v1/files/**")
                .addResourceLocations("file:uploads/courses/");
        resourceHandlerRegistry.addResourceHandler("/api/v1/files/categories/**")
            .addResourceLocations("file:uploads/categories/");
    }
}
