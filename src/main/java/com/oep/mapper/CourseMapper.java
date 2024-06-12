package com.oep.mapper;

import com.oep.domain.Course;
import com.oep.dto.request.CreateCourseRequest;
import com.oep.dto.response.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CourseMapper {

    CourseResponse toCourseResponse(Course course);
    Course toCourse(CreateCourseRequest request);
    void update(CreateCourseRequest request, @MappingTarget Course course);

}
