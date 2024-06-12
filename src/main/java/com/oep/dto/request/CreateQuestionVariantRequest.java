package com.oep.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateQuestionVariantRequest {

    private String variantText;
    private Boolean isCorrect;

}
