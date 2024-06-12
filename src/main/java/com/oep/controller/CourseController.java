package com.oep.controller;

import com.oep.domain.Course;
import com.oep.dto.request.CreateCourseRequest;
import com.oep.dto.response.CourseResponse;
import com.oep.dto.response.CourseSubscriptionResponse;
import com.oep.service.CourseService;
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
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CourseResponse create(@RequestBody @Valid CreateCourseRequest request) {
        return courseService.create(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<CourseResponse> getAll(@QuerydslPredicate(root = Course.class) Predicate filters,
                                       @PageableDefault Pageable pageable) {
        return courseService.getAll(filters, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CourseResponse getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CourseResponse update(@PathVariable Long id, @RequestBody @Valid CreateCourseRequest request) {
        return courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }

    @PutMapping("/{courseId}/grades/{gradeId}")
    public CourseSubscriptionResponse attachGradeToCourse(@PathVariable Long courseId, @PathVariable Long gradeId) {
        return courseService.attachGradeToCourse(courseId, gradeId);
    }
}
