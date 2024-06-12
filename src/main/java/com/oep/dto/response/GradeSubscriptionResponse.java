package com.oep.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeSubscriptionResponse {

    private Long userId;
    private Long gradeId;

}
