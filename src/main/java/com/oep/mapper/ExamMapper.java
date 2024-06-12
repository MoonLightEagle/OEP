package com.oep.mapper;

import com.oep.domain.Exam;
import com.oep.dto.request.CreateExamRequest;
import com.oep.dto.response.ExamResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {QuestionMapper.class}
)
public interface ExamMapper {

    @Mapping(source = "questions", target = "questions", qualifiedByName = "toQuestionResponse")
    ExamResponse toExamResponse(Exam exam);
    Exam toExam(CreateExamRequest request);
    void update(CreateExamRequest request, @MappingTarget Exam exam);



}
