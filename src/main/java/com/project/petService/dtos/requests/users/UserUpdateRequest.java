package com.project.petService.dtos.requests.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {
    @Size(min = 1, max = 25, message = "NAME_VALID")
    private String fullName;

    private String image;

    @NotBlank(message = "GENDER_NOT_BLANK")
    private String gender;

    @Size(min = 10, message = "USERNAME_INVALID")
    private String phone;

    private String dateOfBirth;
}
