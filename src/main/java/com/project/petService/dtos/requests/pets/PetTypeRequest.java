package com.project.petService.dtos.requests.pets;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetTypeRequest {
    @NotNull(message = "Tên loại không được để trống")
    String name;

    String description;
}
