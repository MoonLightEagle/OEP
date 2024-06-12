package com.oep.mapper;

import com.oep.domain.QuestionAnswer;
import com.oep.dto.response.ExamResulQuestionResponse;
import com.oep.dto.response.QuestionAnswerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface QuestionAnswerMapper {



    @Mapping(source = "question.questionText", target = "question")
    @Mapping(source = "userAnswer.variantText", target = "answer")
    QuestionAnswerResponse toQuestionAnswerResponse(QuestionAnswer questionAnswer);


    @Mapping(source = "question.questionText", target = "question")
    @Mapping(source = "userAnswer.variantText", target = "answer")
    @Mapping(source = "question.value", target = "questionValue")
    @Mapping(source = "userAnswer.isCorrect", target = "correct")
    ExamResulQuestionResponse toExamResultQuestionResponse(QuestionAnswer questionAnswer);

}
