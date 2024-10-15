package com.project.petService.controllers;

import com.project.petService.dtos.requests.users.UserCreationRequest;
import com.project.petService.dtos.requests.users.UserUpdateRequest;
import com.project.petService.dtos.requests.users.UserUpdateRoleRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.PageResponse;
import com.project.petService.dtos.response.users.UserResponse;
import com.project.petService.services.user.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> createUser(
            @RequestBody @Valid UserCreationRequest request
    ) {
        return ApiResponse.<UserResponse>builder()
                .message("Đăng ký thành công")
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/myInfo")
    public UserResponse getMyInfo() {
        return userService.getMyInfo();
    }

    @GetMapping("")
    public PageResponse<List<UserResponse>> getAllUser(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        Page<UserResponse> userResponses = userService.getAllUsers(page, limit);

        int totalPages = userService.getAllUsers(page, limit).getTotalPages();
        return PageResponse.<List<UserResponse>>builder()
                .message("TotalPages = " + totalPages)
                .currentPage(userResponses.getNumber())
                .totalPages(userResponses.getTotalPages())
                .totalElements(userResponses.getTotalElements())
                .pageSize(userResponses.getSize())
                .result(userResponses.getContent())
                .build();
    }

    @GetMapping("/search")
    public PageResponse<List<UserResponse>> searchUser(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        Page<UserResponse> userResponses = userService.searchUsers(name, page, limit);
        int totalPages = userService.getAllUsers(page, limit).getTotalPages();
        return PageResponse.<List<UserResponse>>builder()
                .message("TotalPages = " + totalPages)
                .currentPage(userResponses.getNumber())
                .totalPages(userResponses.getTotalPages())
                .totalElements(userResponses.getTotalElements())
                .pageSize(userResponses.getSize())
                .result(userResponses.getContent())
                .result(userResponses.getContent())
                .build();
    }

    @GetMapping("/count_user")
    public ApiResponse<Long> getTotalUser(
    ) {
        return ApiResponse.<Long>builder()
                .result(userService.getCountUsers())
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable("userId") String id,
            @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("Cập nhật thông tin thành công")
                .result(userService.updateUser(id, request))
                .build();
    }

    @PutMapping("/addRole/{userId}")
    public ApiResponse<UserResponse> updateUserRole(
            @PathVariable("userId") String id,
            @RequestBody @Valid UserUpdateRoleRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("Cập nhật thông tin thành công")
                .result(userService.updateRole(id, request.getRoleIds()))
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable("userId") String id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .message("Xóa thông tin thành công")
                .build();
    }
}
