package com.project.petService.dtos.requests.users;

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
    @Size(min = 3, max = 30, message = "NAME_VALID")
    private String fullName;

    @NotBlank(message = "GENDER_NOT_BLANK")
    @Pattern(regexp = "^(?i)(Nam|Nữ)$", message = "GENDER_INVALID")
    private String gender;

    @Size(min = 10, max = 10, message = "PHONE_VALID")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "PHONE_FORMAT")
    private String phone;

    @NotBlank(message = "EMAIL_NOT_BLANK")
    @Email(message = "EMAIL_FORMAT")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "EMAIL_FORMAT")
    @Size(min = 10, max = 64, message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "USERNAME_NOT_BLANK")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "USERNAME_FORMAT")
    @Size(min = 3, max = 30, message = "USERNAME_VALID")
    private String username;

    @NotBlank(message = "PASSWORD_NOT_BLANK")
    @Size(min = 6, max = 30, message = "PASSWORD_INVALID")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,64}$",
            message = "PASSWORD_FORMAT"
    )
    private String password;

    @NotBlank(message = "REPASSWORD_NOT_BLANK")
    @Size(min = 6, max = 30)
    private String repassword;
}
