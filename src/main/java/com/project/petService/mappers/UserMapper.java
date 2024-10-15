package com.project.petService.mappers;

import com.project.petService.dtos.requests.users.UserCreationRequest;
import com.project.petService.dtos.requests.users.UserUpdateRequest;
import com.project.petService.dtos.response.users.UserResponse;
import com.project.petService.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
