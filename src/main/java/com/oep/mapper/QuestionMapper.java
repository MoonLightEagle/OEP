package com.oep.mapper;

import com.oep.domain.Question;
import com.oep.domain.QuestionVariant;
import com.oep.dto.request.CreateQuestionRequest;
import com.oep.dto.request.CreateQuestionVariantRequest;
import com.oep.dto.response.QuestionResponse;
import com.oep.dto.response.QuestionVariantResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface QuestionMapper {

    @Named("toQuestionResponse")
    @Mapping(source = "variants", target = "variants", qualifiedByName = "toQuestionVariant")
    QuestionResponse toQuestionResponse(Question save);

    @Named("toQuestionVariant")
    @IterableMapping(qualifiedByName = "toQuestionVariantResponse")
    List<QuestionVariantResponse> toQuestionVariantResponse(List<QuestionVariant> variants);

    @Named("toQuestionVariantResponse")
    QuestionVariantResponse toQuestionVariantResponse(QuestionVariant variant);

    @Mapping(source = "variants", target = "variants", qualifiedByName = "toVariant")
    Question toQuestion(CreateQuestionRequest request);

    void update(CreateQuestionRequest request, @MappingTarget Question question);

    @Named("toVariant")
    @IterableMapping(qualifiedByName = "toQuestionVariant")
    List<QuestionVariant> toQuestionVariant(List<CreateQuestionVariantRequest> request);


    @Named("toQuestionVariant")
    QuestionVariant toQuestionVariant(CreateQuestionVariantRequest request);

}
