package com.oep.service;

import com.oep.domain.Course;
import com.oep.domain.Exam;
import com.oep.domain.Grade;
import com.oep.domain.Question;
import com.oep.domain.QuestionAnswer;
import com.oep.domain.QuestionVariant;
import com.oep.domain.User;
import com.oep.dto.request.CreateExamRequest;
import com.oep.dto.request.SubmitExamRequest;
import com.oep.dto.request.SubmitQuestionRequest;
import com.oep.dto.response.ExamResponse;
import com.oep.dto.response.ExamResulQuestionResponse;
import com.oep.dto.response.ExamResultResponse;
import com.oep.dto.response.QuestionAnswerResponse;
import com.oep.exception.ServiceException;
import com.oep.mapper.ExamMapper;
import com.oep.mapper.QuestionAnswerMapper;
import com.oep.repository.CourseRepository;
import com.oep.repository.ExamRepository;
import com.oep.repository.QuestionAnswerRepository;
import com.oep.repository.QuestionRepository;
import com.oep.repository.QuestionVariantRepository;
import com.oep.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamMapper examMapper;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuestionVariantRepository questionVariantRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuestionAnswerMapper questionAnswerMapper;

    @Value("${oep.exam.percentageCap}")
    private Double percentageCap;

    @Transactional
    public ExamResponse create(CreateExamRequest request) {
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow();

        Exam examToSave = examMapper.toExam(request);
        examToSave.setCourse(course);

        Exam newlyCreatedExam = examRepository.save(examToSave);
        return examMapper.toExamResponse(newlyCreatedExam);
    }

    public Page<ExamResponse> getAll(Predicate filters, Pageable pageable) {
        return examRepository.findAll(filters, pageable).map(examMapper::toExamResponse);
    }

    public ExamResponse getById(Long id) {
        return examRepository.findById(id).map(examMapper::toExamResponse).orElseThrow();
    }

    @Transactional
    public ExamResponse update(Long id, CreateExamRequest request) {
        return examRepository.findById(id)
                .map(exam -> {
                    examMapper.update(request, exam);
                    return examMapper.toExamResponse(exam);
                })
                .orElseThrow();
    }

    @Transactional
    public void delete(Long id) {
        examRepository.deleteById(id);
    }

    public List<QuestionAnswerResponse> submitExam(Long id, SubmitExamRequest submitExamRequest) {
        Exam exam = examRepository.findById(id).orElseThrow();
        User user = userRepository.findById(submitExamRequest.getUserId()).orElseThrow();

        if (!userIsEligibleForEx(exam, user)) {
            throw new ServiceException("You do not belong to the course %s".formatted(exam.getCourse().getName()));
        }

        if (LocalDateTime.now().isAfter(exam.getEndTime()) || LocalDateTime.now().isBefore(exam.getStartTime())) {
            throw new ServiceException("Exam has not been starter yet");
        }


        List<QuestionAnswer> userQuestionAnswers = questionAnswerRepository.findAllByUserId(user.getId());
        if (!userQuestionAnswers.isEmpty()) {
            throw new ServiceException("User has already submitted an exam!");
        }

        List<QuestionAnswer> questionAnswers = generateQuestionAnswers(submitExamRequest, user);
        List<QuestionAnswer> questionAnswersSaved = questionAnswerRepository.saveAll(questionAnswers);

        return questionAnswersSaved.stream()
                .map(questionAnswerMapper::toQuestionAnswerResponse)
                .toList();
    }

    public ExamResultResponse getResults(Long examId, Long userId) {
        Exam exam = examRepository.findById(examId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        List<QuestionAnswer> questionAnswers = exam.getQuestions().stream()
                .flatMap(question -> question.getQuestionAnswers().stream())
                .filter(answer -> answer.getUser().getId().equals(userId))
                .toList();

        if (LocalDateTime.now().isBefore(exam.getEndTime())) {
            throw new ServiceException("Exam hasn't been finished yet! Try after %s".formatted(exam.getEndTime()));
        }

        if (questionAnswers.isEmpty()) {
            throw new ServiceException("User has not submitted the exam yet!");
        }

        List<ExamResulQuestionResponse> questionResponses = questionAnswers.stream()
                .map(questionAnswerMapper::toExamResultQuestionResponse)
                .collect(Collectors.toList());

        List<ExamResulQuestionResponse> correctQuestions = questionResponses.stream()
                .filter(ExamResulQuestionResponse::getCorrect)
                .toList();

        int mark = correctQuestions.stream()
                .mapToInt(ExamResulQuestionResponse::getQuestionValue)
                .sum();

        long totalNumberOfQuestions = questionResponses.size();
        long numberOfCorrectQuestions = correctQuestions.size();

        double correctQuestionsPercentage = totalNumberOfQuestions == 0 ? 0 :
                ((double) numberOfCorrectQuestions / totalNumberOfQuestions) * 100;

        boolean passed = getPassed(correctQuestionsPercentage);

        return new ExamResultResponse(exam.getId(), user.getId(), mark, correctQuestionsPercentage, passed, questionResponses);
    }

    private static boolean userIsEligibleForEx(Exam exam, User user) {
        List<Long> examGrades = exam.getCourse().getGrades().stream().map(Grade::getId).toList();
        List<Long> userGrades = user.getGrades().stream().map(Grade::getId).toList();

        return userGrades.stream().anyMatch(examGrades::contains);
    }

    private boolean getPassed(double completionPercentage) {
        return completionPercentage >= percentageCap;
    }

    private List<QuestionAnswer> generateQuestionAnswers(SubmitExamRequest submitExamRequest, User user) {
        return submitExamRequest.getSubmitQuestionRequests().stream()
                .map(this::toQuestionAnswer)
                .peek(questionAnswer -> questionAnswer.setUser(user))
                .toList();

    }

    private QuestionAnswer toQuestionAnswer(SubmitQuestionRequest submitQuestionRequest) {
        Question question = questionRepository.findById(submitQuestionRequest.getQuestionId()).orElseThrow();
        QuestionVariant questionVariant = questionVariantRepository.findById(submitQuestionRequest.getVariantId()).orElseThrow();

        return QuestionAnswer.builder()
                .question(question)
                .userAnswer(questionVariant)
                .build();
    }

}
