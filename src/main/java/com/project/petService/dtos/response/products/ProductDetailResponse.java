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
public class ProductDetailResponse {
    Long id;

    String name;

    String description;

    Double price;

    Float star;

    String brand;

    String status;

    SubCategoryResponse subCategory;

    Set<ProductImageResponse> productImages;

    Set<AttributeResponse> attributes;
}
