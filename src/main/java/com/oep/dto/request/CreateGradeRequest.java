package com.oep.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateGradeRequest {

    private String name;
    private String description;

}
