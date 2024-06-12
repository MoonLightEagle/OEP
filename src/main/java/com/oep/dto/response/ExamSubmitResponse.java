package com.oep.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ExamSubmitResponse {

    private List<QuestionAnswerResponse> questionAnswerResponses;

}
