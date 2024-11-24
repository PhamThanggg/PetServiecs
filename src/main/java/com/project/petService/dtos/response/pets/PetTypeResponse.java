package com.project.petService.dtos.response.pets;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetTypeResponse {
    Long id;

    String name;

    String description;
}
