package com.oep.service;

import com.oep.domain.Grade;
import com.oep.domain.User;
import com.oep.dto.request.CreateGradeRequest;
import com.oep.dto.response.GradeResponse;
import com.oep.dto.response.GradeSubscriptionResponse;
import com.oep.mapper.GradeMapper;
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
public class GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final GradeMapper gradeMapper;

    public GradeResponse create(CreateGradeRequest request) {
        return gradeMapper.toGradeResponse(gradeRepository.save(gradeMapper.toGrade(request)));
    }

    public Page<GradeResponse> getAll(Predicate filters, Pageable pageable) {
        return gradeRepository.findAll(filters, pageable).map(gradeMapper::toGradeResponse);
    }

    public GradeResponse getById(Long id) {
        return gradeRepository.findById(id).map(gradeMapper::toGradeResponse).orElseThrow();
    }

    @Transactional
    public GradeResponse update(Long id, CreateGradeRequest request) {
        return gradeRepository.findById(id)
                .map(grade -> {
                    gradeMapper.update(request, grade);
                    return gradeMapper.toGradeResponse(grade);
                })
                .orElseThrow();
    }

    @Transactional
    public void delete(Long id) {
        gradeRepository.deleteById(id);
    }

    @Transactional
    public GradeSubscriptionResponse addUserToGrade(Long gradeId, Long userId) {
        Grade grade = gradeRepository.findById(gradeId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        grade.getUsers().add(user);
        user.getGrades().add(grade);

        userRepository.save(user);
        gradeRepository.save(grade);

        return new GradeSubscriptionResponse(user.getId(), grade.getId());
    }
}
