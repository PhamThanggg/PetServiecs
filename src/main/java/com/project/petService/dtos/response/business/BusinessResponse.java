package com.project.petService.dtos.response.business;

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
