package com.oep.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateQuestionRequest {

    private String questionText;
    private Integer value;
    private List<CreateQuestionVariantRequest> variants;

}
