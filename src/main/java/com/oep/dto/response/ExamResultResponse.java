package com.oep.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExamResultResponse {

    private Long examId;
    private Long userId;
    private Integer mark;
    private Double completionPercentage;
    private Boolean passed;
    private List<ExamResulQuestionResponse> resultPerQuestion;

}
