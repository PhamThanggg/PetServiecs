package com.project.petService.mappers;

import com.project.petService.dtos.requests.users.PermissionRequest;
import com.project.petService.dtos.requests.users.PermissionUpdateRequest;
import com.project.petService.dtos.response.users.PermissionResponse;
import com.project.petService.entities.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface    PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    void updatePermission(@MappingTarget Permission permission, PermissionUpdateRequest request);
}
