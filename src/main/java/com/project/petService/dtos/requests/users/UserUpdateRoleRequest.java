package com.project.petService.dtos.requests.users;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRoleRequest {
    @NotEmpty(message = "ROLE_NOT_BLANK")
    private Set<Long> roleIds;
}
