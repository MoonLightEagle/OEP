package com.oep.mapper;

import com.oep.domain.Grade;
import com.oep.dto.request.CreateGradeRequest;
import com.oep.dto.response.GradeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GradeMapper {

    GradeResponse toGradeResponse(Grade grade);
    Grade toGrade(CreateGradeRequest request);
    void update(CreateGradeRequest request, @MappingTarget Grade grade);

}
