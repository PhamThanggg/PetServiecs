package com.project.petService.mappers;


import com.project.petService.dtos.requests.products.SubCategoryRequest;
import com.project.petService.dtos.response.products.SubCategoryResponse;
import com.project.petService.entities.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {
    SubCategory toSubCategory(SubCategoryRequest request);

    SubCategoryResponse toSubCategoryResponse(SubCategory category);

    void updateSubCategory(@MappingTarget SubCategory category, SubCategoryRequest request);
}
