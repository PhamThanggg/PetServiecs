package com.project.petService.dtos.requests.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    @NotBlank(message = "CATEGORY_NOT_BLANK")
    @Size(min = 2,max = 30, message = "CATEGORY_INVALID")
    String name;
}
