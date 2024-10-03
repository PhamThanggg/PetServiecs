package com.project.petService.mappers;


import com.project.petService.dtos.requests.products.ProductImageRequest;
import com.project.petService.dtos.response.products.ProductImageResponse;
import com.project.petService.entities.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImage toProductImage(ProductImageRequest request);
    ProductImageResponse toProductImageResponse(ProductImage movieImage);
    void updateProductImage(@MappingTarget ProductImage productImage, ProductImageRequest request);
}
