package com.project.petService.dtos.response.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCartResponse {
    Long id;

    String name;

    Double price;

    CategoryResponse category;

    Set<ProductImageResponse> productImages;
}
