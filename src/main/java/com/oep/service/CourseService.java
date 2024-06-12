package com.oep.service;

import com.oep.domain.Course;
import com.oep.domain.Grade;
import com.oep.domain.User;
import com.oep.dto.request.CreateCourseRequest;
import com.oep.dto.response.CourseResponse;
import com.oep.dto.response.CourseSubscriptionResponse;
import com.oep.mapper.CourseMapper;
import com.oep.repository.CourseRepository;
import com.oep.repository.GradeRepository;
import com.oep.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    public CourseResponse create(CreateCourseRequest request) {
        User user = userRepository.findById(request.getTeacherId()).orElseThrow();
        Course course = courseMapper.toCourse(request);

        course.setUser(user);
        user.getCourses().add(course);
        Course savedCourse = courseRepository.save(course);
        userRepository.save(user);

        return courseMapper.toCourseResponse(savedCourse);
    }

    public Page<CourseResponse> getAll(Predicate filters, Pageable pageable) {
        return courseRepository.findAll(filters, pageable).map(courseMapper::toCourseResponse);
    }

    public CourseResponse getById(Long id) {
        return courseRepository.findById(id).map(courseMapper::toCourseResponse).orElseThrow();
    }

    @Transactional
    public CourseResponse update(Long id, CreateCourseRequest request) {
        return courseRepository.findById(id)
                .map(course -> {
                    courseMapper.update(request, course);
                    return courseMapper.toCourseResponse(course);
                })
                .orElseThrow();
    }

    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    public CourseSubscriptionResponse attachGradeToCourse(Long courseId, Long gradeId) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        Grade grade = gradeRepository.findById(gradeId).orElseThrow();

        course.getGrades().add(grade);
        grade.getCourses().add(course);

        courseRepository.save(course);
        gradeRepository.save(grade);

        return new CourseSubscriptionResponse(courseId, gradeId);
    }

}
