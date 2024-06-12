package com.oep.controller;

import com.oep.dto.request.CreateQuestionVariantRequest;
import com.oep.dto.response.QuestionResponse;
import com.oep.service.QuestionVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionVariantController {

    private final QuestionVariantService questionVariantService;

    @PutMapping("/{id}/variants")
    public QuestionResponse questionResponse(@PathVariable Long id, @RequestBody CreateQuestionVariantRequest createQuestionVariantRequest) {
        return questionVariantService.addVariant(id, createQuestionVariantRequest);
    }

}
