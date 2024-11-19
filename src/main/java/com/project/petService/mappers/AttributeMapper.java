package com.project.petService.mappers;


import com.project.petService.dtos.requests.products.AttributeRequest;
import com.project.petService.dtos.response.products.AttributeResponse;
import com.project.petService.entities.Attribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
    Attribute toAttribute(AttributeRequest request);

    @Mapping(source = "product.id", target = "productId")
    AttributeResponse toAttributeResponse(Attribute attribute);

    void updateAttribute(@MappingTarget Attribute attribute, AttributeRequest request);
}
