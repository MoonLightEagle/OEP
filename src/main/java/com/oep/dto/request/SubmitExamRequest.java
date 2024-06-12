package com.oep.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SubmitExamRequest {

    private Long userId;
    private List<SubmitQuestionRequest> submitQuestionRequests;

}
