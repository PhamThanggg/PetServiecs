package com.project.petService.mappers;

import com.project.petService.dtos.requests.users.RoleRequest;
import com.project.petService.dtos.response.users.RoleResponse;
import com.project.petService.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    void updateRole(@MappingTarget Role role, RoleRequest request);

}
