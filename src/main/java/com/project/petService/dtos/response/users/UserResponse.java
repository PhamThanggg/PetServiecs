package com.project.petService.dtos.response.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String fullName;
    private String image;
    private String gender;
    private String phone;
    private String dateOfBirth;
    private String email;
    private int status;
    private Long shopStart;
    private Set<RoleResponse> roles;
}
