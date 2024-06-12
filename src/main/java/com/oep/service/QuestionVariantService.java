package com.oep.service;

import com.oep.domain.Question;
import com.oep.domain.QuestionVariant;
import com.oep.dto.request.CreateQuestionVariantRequest;
import com.oep.dto.response.QuestionResponse;
import com.oep.mapper.QuestionMapper;
import com.oep.repository.QuestionRepository;
import com.oep.repository.QuestionVariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionVariantService {

    private final QuestionRepository questionRepository;
    private final QuestionVariantRepository questionVariantRepository;
    private final QuestionMapper questionMapper;

    @Transactional
    public QuestionResponse addVariant(Long id, CreateQuestionVariantRequest createQuestionVariantRequest) {
        Question question = questionRepository.findById(id).orElseThrow();
        QuestionVariant questionVariant = questionMapper.toQuestionVariant(createQuestionVariantRequest);
        question.getVariants().add(questionVariant);
        questionVariant.setQuestion(question);

        Question savedQuestion = questionRepository.save(question);
        questionVariantRepository.save(questionVariant);
        return questionMapper.toQuestionResponse(savedQuestion);
    }

}
