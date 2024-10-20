package com.project.petService.mappers;


import com.project.petService.dtos.requests.products.ProductRequest;
import com.project.petService.dtos.response.products.ProductResponse;
import com.project.petService.entities.Product;
import com.project.petService.entities.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);

//    @Mapping(source = "category.id", target = "categoryId")
    ProductResponse toProductResponse(Product product);

    void updateProduct(@MappingTarget Product product, ProductRequest request);

}
