package com.oep.service;

import com.oep.domain.Exam;
import com.oep.domain.Question;
import com.oep.dto.request.CreateQuestionRequest;
import com.oep.dto.response.ExamResponse;
import com.oep.mapper.ExamMapper;
import com.oep.mapper.QuestionMapper;
import com.oep.repository.ExamRepository;
import com.oep.repository.QuestionRepository;
import com.oep.repository.QuestionVariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamQuestionService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final QuestionVariantRepository questionVariantRepository;
    private final ExamMapper examMapper;
    private final QuestionMapper questionMapper;

    @Transactional
    public ExamResponse addQuestion(Long id, CreateQuestionRequest createQuestionRequest) {
        Exam exam = examRepository.findById(id).orElseThrow();
        Question question = toQuestionVariants(questionMapper.toQuestion(createQuestionRequest));

        exam.getQuestions().add(question);
        question.setExam(exam);

        examRepository.save(exam);
        questionRepository.save(question);
        questionVariantRepository.saveAll(question.getVariants());

        return examRepository.findById(id)
                .map(examMapper::toExamResponse)
                .orElseThrow();
    }

    private Question toQuestionVariants(Question question) {
        question.getVariants().forEach(questionVariant -> questionVariant.setQuestion(question));
        return question;
    }


}
