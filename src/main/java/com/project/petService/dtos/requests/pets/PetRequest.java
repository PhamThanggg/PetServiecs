package com.project.petService.dtos.requests.pets;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetRequest {
    @NotNull(message = "Tên thú cưng không được để trống")
    String name;

    @NotNull(message = "Tuổi không được để trống")
    @Min(value = 1, message = "Tuổi >= 1")
    int age;

    @NotNull(message = "Cân nặng không được để trống")
    @Min(value = 1, message = "Cân nặng >= 1")
    Float weight;

    @NotNull(message = "Màu không được để trống")
    String color;

    @NotNull(message = "Bạn chưa chọn loại thú cưng")
    @Min(value = 1, message = "ID loại thú cưng phải >= 1")
    Long petTypeId;
}
