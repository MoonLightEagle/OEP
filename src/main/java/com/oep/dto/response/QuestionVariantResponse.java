package com.oep.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionVariantResponse {

    private Long id;
    private String variantText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
