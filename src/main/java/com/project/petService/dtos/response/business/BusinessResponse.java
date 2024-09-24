package com.project.petService.dtos.response.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessResponse {
    String name;

    String address;

    String phone;

    String email;

    Set<Long> businessTypeId;

    Long areaId;
}
