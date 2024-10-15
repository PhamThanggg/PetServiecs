package com.project.petService.dtos.requests.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "USERNAME_NOT_BLANK")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "USERNAME_FORMAT")
    private String username;

    @NotBlank(message = "PASSWORD_NOT_BLANK")
    private String password;
}
