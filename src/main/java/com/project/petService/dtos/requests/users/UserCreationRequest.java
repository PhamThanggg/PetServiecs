package com.project.petService.dtos.requests.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationRequest {
    @NotBlank(message = "NAME_NOT_BLANK")
    @Size(min = 1, max = 30, message = "NAME_VALID")
    private String fullName;

    @NotBlank(message = "GENDER_NOT_BLANK")
    private String gender;

    @Size(min = 10, max = 30, message = "PHONE_VALID")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "PHONE_FORMAT")
    private String phone;

    @NotBlank(message = "EMAIL_NOT_BLANK")
    @Email(message = "EMAIL_FORMAT")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "EMAIL_FORMAT")
    private String email;

    @NotBlank(message = "USERNAME_NOT_BLANK")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "USERNAME_FORMAT")
    private String username;

    @NotBlank(message = "PASSWORD_NOT_BLANK")
    @Size(min = 6, message = "PASSWORD_INVALID")
    private String password;

    @NotBlank(message = "REPASSWORD_NOT_BLANK")
    private String repassword;

    private int status;

}
