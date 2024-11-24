package com.project.petService.dtos.response.pets;

import com.project.petService.entities.PetType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetResponse {
    Long id;

    String name;

    int age;

    Float weight;

    String color;

    PetType petType;
}
