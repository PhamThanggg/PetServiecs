package com.project.petService.services.user;

import com.project.petService.dtos.requests.users.RoleRequest;
import com.project.petService.dtos.response.users.RoleResponse;
import com.project.petService.entities.Role;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.RoleMapper;
import com.project.petService.repositories.PermissionRepository;
import com.project.petService.repositories.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @PreAuthorize("hasAuthority('MANAGE_ROLE')")
    public RoleResponse create(RoleRequest request) {
        if(roleRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.ROLE_EXISTS);
        }

        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissionIds());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @PreAuthorize("hasAuthority('MANAGE_ROLE')")
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public RoleResponse getById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTS));
        return roleMapper.toRoleResponse(role);
    }

    @PreAuthorize("hasAuthority('MANAGE_ROLE')")
    public RoleResponse update(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ROLE_NOT_EXISTS)
        );
        var permissions = permissionRepository.findAllById(request.getPermissionIds());
        role.setPermissions(new HashSet<>(permissions));
        roleMapper.updateRole(role, request);
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @PreAuthorize("hasAuthority('MANAGE_ROLE')")
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    public Page<RoleResponse> getAllRole(int page, int limit, String name) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return roleRepository.findRoleName(name, pageable).map(roleMapper::toRoleResponse);
    }
}
