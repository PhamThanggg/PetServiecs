package com.project.petService.controllers;

import com.project.petService.dtos.requests.users.PermissionRequest;
import com.project.petService.dtos.requests.users.PermissionUpdateRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.users.PermissionResponse;
import com.project.petService.services.user.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @PatchMapping("/{id}")
    ApiResponse<PermissionResponse> update(
        @RequestBody PermissionUpdateRequest request,
        @PathVariable("id") Long id
    ) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> delete(@PathVariable Long id) {
//        permissionService.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
