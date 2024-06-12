package com.oep.dto.request;

import lombok.Data;

@Data
public class CreateCourseRequest {

    private String name;
    private String description;
    private Long teacherId;

}
