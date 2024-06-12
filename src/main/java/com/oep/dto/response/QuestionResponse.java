package com.oep.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionResponse {

    private Long id;
    private String questionText;
    private Integer value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<QuestionVariantResponse> variants;

}
