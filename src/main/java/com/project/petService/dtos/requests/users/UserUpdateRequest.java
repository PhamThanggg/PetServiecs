package com.project.petService.dtos.requests.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Size(min = 1, max = 30, message = "NAME_VALID")
    private String fullName;

//    private String image;

    @NotBlank(message = "GENDER_NOT_BLANK")
    private String gender;

    @Size(min = 10, max = 10, message = "PHONE_VALID")
    @Pattern(
            regexp = "^(\\+84|0)(3[2-9]|5[6|8|9]|7[0-9]|8[1-9]|9[0-9])[0-9]{7}$",
            message = "PHONE_FORMAT"
    )
    private String phone;

    private String dateOfBirth;
}
