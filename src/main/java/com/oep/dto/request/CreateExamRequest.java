package com.oep.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateExamRequest {

    private String name;
    private Long courseId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
