package com.oep.controller;

import com.oep.dto.request.CreateQuestionRequest;
import com.oep.dto.response.ExamResponse;
import com.oep.service.ExamQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamQuestionController {

    private final ExamQuestionService examService;


    @PutMapping("/{id}/questions")
    public ExamResponse addQuestion(@PathVariable Long id, @RequestBody CreateQuestionRequest createQuestionRequest) {
        return examService.addQuestion(id, createQuestionRequest);
    }
}
