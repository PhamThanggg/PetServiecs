package com.project.petService.dtos.requests.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.petService.entities.Area;
import com.project.petService.entities.BusinessType;
import com.project.petService.entities.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessRequest {
    @JsonProperty("name")
    @NotNull(message = "BUSINESS_NAME_NOT_BLANK")
    @Size(min = 2,max = 30, message = "BUSINESS_NAME_INVALID")
    String name;

    @NotNull(message = "ADDRESS_NOT_BLANK")
    @Size(min = 2,max = 60, message = "ADDRESS_INVALID")
    String address;


    @Size(min = 10, max = 10, message = "PHONE_INVALID")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "PHONE_FORMAT")
    String phone;

    @NotBlank(message = "EMAIL_NOT_BLANK")
    @Email(message = "EMAIL_INVALID")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "EMAIL_INVALID")
    String email;

    @NotEmpty(message = "BUSINESS_TYPE_NOT_EMPTY")
    Set<Long> businessTypeId;

    @NotNull(message = "BUSINESS_AREA_NOT_NULL")
    @Min(value = 1, message = "BUSINESS_AREA_INVALID")
    Long areaId;

}
