package com.oep.dto.response;

import lombok.Data;

@Data
public class ExamResulQuestionResponse {

    private String question;
    private Integer questionValue;
    private String answer;
    private Boolean correct;

}
