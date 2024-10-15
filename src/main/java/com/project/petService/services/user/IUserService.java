package com.project.petService.services.user;

import com.project.petService.dtos.requests.users.UserCreationRequest;
import com.project.petService.dtos.requests.users.UserUpdateRequest;
import com.project.petService.dtos.response.users.UserResponse;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface IUserService {
    UserResponse createUser(UserCreationRequest request);

    UserResponse getMyInfo();

    Page<UserResponse> getAllUsers(int page, int limit);

    Page<UserResponse> searchUsers(String search, int page, int limit);

    Long getCountUsers();

    UserResponse updateUser(String id, UserUpdateRequest request);

    UserResponse updateRole(String id, Set<Long> roleIds);

    void deleteUser(String userId);

}
