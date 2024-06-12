package com.oep.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseSubscriptionResponse {

    private Long courseId;
    private Long gradeId;

}
