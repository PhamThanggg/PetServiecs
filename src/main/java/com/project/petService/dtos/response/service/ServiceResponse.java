package com.project.petService.dtos.response.service;

import com.project.petService.entities.Business;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceResponse {
    Long id;

    String name;

    Business business;
}
