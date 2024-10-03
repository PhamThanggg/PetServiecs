package com.project.petService.mappers;


import com.project.petService.dtos.requests.products.CategoryRequest;
import com.project.petService.dtos.response.products.CategoryResponse;
import com.project.petService.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
