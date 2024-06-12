package com.oep.controller;

import com.oep.domain.Exam;
import com.oep.dto.request.CreateExamRequest;
import com.oep.dto.request.SubmitExamRequest;
import com.oep.dto.response.ExamResponse;
import com.oep.dto.response.ExamResultResponse;
import com.oep.dto.response.QuestionAnswerResponse;
import com.oep.service.ExamService;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ExamResponse create(@RequestBody @Valid CreateExamRequest request) {
        return examService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<ExamResponse> getAll(@QuerydslPredicate(root = Exam.class) Predicate filters,
                                     @PageableDefault Pageable pageable) {
        return examService.getAll(filters, pageable);
    }

    @GetMapping("/{id}")
    public ExamResponse getById(@PathVariable Long id) {
        return examService.getById(id);
    }

    @PutMapping("/{id}")
    public ExamResponse update(@PathVariable Long id, @RequestBody @Valid CreateExamRequest request) {
        return examService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        examService.delete(id);
    }

    @PutMapping("/{id}/submit")
    public List<QuestionAnswerResponse> submitResult(@PathVariable Long id, @RequestBody SubmitExamRequest submitExamRequest) {
        return examService.submitExam(id, submitExamRequest);
    }

    @GetMapping("/{examId}/users/{userId}")
    public ExamResultResponse getExamResult(@PathVariable Long examId, @PathVariable Long userId) {
        return examService.getResults(examId, userId);
    }

}
