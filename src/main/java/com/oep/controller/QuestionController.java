package com.oep.controller;

import com.oep.domain.Question;
import com.oep.dto.request.CreateQuestionRequest;
import com.oep.dto.response.QuestionResponse;
import com.oep.service.QuestionService;
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

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public QuestionResponse create(@RequestBody @Valid CreateQuestionRequest request) {
        return questionService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<QuestionResponse> getAll(@QuerydslPredicate(root = Question.class) Predicate filters,
                                     @PageableDefault Pageable pageable) {
        return questionService.getAll(filters, pageable);
    }

    @GetMapping("/{id}")
    public QuestionResponse getById(@PathVariable Long id) {
        return questionService.getById(id);
    }

    @PutMapping("/{id}")
    public QuestionResponse update(@PathVariable Long id, @RequestBody @Valid CreateQuestionRequest request) {
        return questionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        questionService.delete(id);
    }

}
