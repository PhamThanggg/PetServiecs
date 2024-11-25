package com.project.petService.dtos.requests.service;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceRequest {
    @NotNull(message = "Tên dịch vụ không được để trống")
    String name;

    Long businessId;
}
