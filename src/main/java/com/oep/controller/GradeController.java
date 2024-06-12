package com.oep.controller;

import com.oep.domain.Grade;
import com.oep.dto.request.CreateGradeRequest;
import com.oep.dto.response.GradeResponse;
import com.oep.dto.response.GradeSubscriptionResponse;
import com.oep.service.GradeService;
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
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GradeResponse create(@RequestBody @Valid CreateGradeRequest request) {
        return gradeService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<GradeResponse> getAll(@QuerydslPredicate(root = Grade.class) Predicate filters,
                                     @PageableDefault Pageable pageable) {
        return gradeService.getAll(filters, pageable);
    }

    @GetMapping("/{id}")
    public GradeResponse getById(@PathVariable Long id) {
        return gradeService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GradeResponse update(@PathVariable Long id, @RequestBody @Valid CreateGradeRequest request) {
        return gradeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        gradeService.delete(id);
    }

    @PutMapping("/{gradeId}/users/{userId}")
    public GradeSubscriptionResponse addUserToGrade(@PathVariable Long gradeId, @PathVariable Long userId) {
        return gradeService.addUserToGrade(gradeId, userId);
    }

}
