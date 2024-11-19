package com.project.petService.dtos.response.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    Long id;

    String name;

    String description;

    Double price;

    Float star;

    String brand;

    String status;

    int percentDiscount;

    SubCategoryResponse subCategory;

    Set<ProductImageResponse> productImages;
}
