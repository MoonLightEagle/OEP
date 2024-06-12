package com.oep.service;

import com.oep.domain.Question;
import com.oep.dto.request.CreateQuestionRequest;
import com.oep.dto.response.QuestionResponse;
import com.oep.mapper.QuestionMapper;
import com.oep.repository.QuestionRepository;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public QuestionResponse create(CreateQuestionRequest request) {
        return questionMapper.toQuestionResponse(questionRepository.save(questionMapper.toQuestion(request)));
    }

    public Page<QuestionResponse> getAll(Predicate filters, Pageable pageable) {
        return questionRepository.findAll(filters, pageable).map(questionMapper::toQuestionResponse);
    }

    public QuestionResponse getById(Long id) {
        return questionRepository.findById(id).map(questionMapper::toQuestionResponse).orElseThrow();
    }

    @Transactional
    public QuestionResponse update(Long id, CreateQuestionRequest request) {
        return questionRepository.findById(id)
                .map(question -> {
                    questionMapper.update(request, question);
                    return questionMapper.toQuestionResponse(question);
                })
                .orElseThrow();
    }

    @Transactional
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    public Question createQuestion(CreateQuestionRequest createQuestionRequest) {
        return questionRepository.save(questionMapper.toQuestion(createQuestionRequest));
    }

}
