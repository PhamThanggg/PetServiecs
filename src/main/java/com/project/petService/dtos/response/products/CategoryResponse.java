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
public class CategoryResponse {
    Long id;
    String name;
    Set<SubCategoryResponse> subCategory;
}
