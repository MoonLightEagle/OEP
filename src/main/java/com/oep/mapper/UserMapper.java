package com.oep.mapper;

import com.oep.domain.User;
import com.oep.dto.request.CreateUserRequest;
import com.oep.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserResponse toUserResponse(User user);
    User toUser(CreateUserRequest request);
    void update(CreateUserRequest request, @MappingTarget User user);

}
