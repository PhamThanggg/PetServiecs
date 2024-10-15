package com.project.petService.services.user;

import com.project.petService.dtos.requests.users.PermissionRequest;
import com.project.petService.dtos.requests.users.PermissionUpdateRequest;
import com.project.petService.dtos.response.users.PermissionResponse;
import com.project.petService.entities.Permission;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.PermissionMapper;
import com.project.petService.repositories.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse create(PermissionRequest request) {
        if(permissionRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.PERMISSION_EXISTS);
        }

        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse update(Long id, PermissionUpdateRequest request) {
        Permission permission = permissionRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PERMISSION_NOT_EXISTS)
        );

        permissionMapper.updatePermission(permission, request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }


    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }
}
