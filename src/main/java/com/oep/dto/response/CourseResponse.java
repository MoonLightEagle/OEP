package com.oep.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
