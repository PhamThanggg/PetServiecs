package com.project.petService.controllers;

import com.project.petService.dtos.requests.users.RoleRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.PageResponse;
import com.project.petService.dtos.response.users.RoleResponse;
import com.project.petService.services.user.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @GetMapping("/search")
    public PageResponse<List<RoleResponse>> searchAll(@RequestParam("page") int page,
                                                      @RequestParam(name = "name", required = false) String name,
                                                      @RequestParam("limit") int limit) {
        Page<RoleResponse> roleResponses = roleService.getAllRole(page, limit, name);
        return PageResponse.<List<RoleResponse>>builder()
                .currentPage(roleResponses.getNumber())
                .totalPages(roleResponses.getTotalPages())
                .totalElements(roleResponses.getTotalElements())
                .pageSize(roleResponses.getSize())
                .result(roleResponses.getContent())
                .build();
    }

    @GetMapping("get/{id}")
    ApiResponse<RoleResponse> getById(
            @PathVariable("id") Long id
    ) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<RoleResponse> update(
            @RequestBody RoleRequest request,
            @PathVariable("id") Long id
    ) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
