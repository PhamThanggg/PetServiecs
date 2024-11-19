package com.project.petService.mappers;


import com.project.petService.dtos.requests.products.AttributeTypeRequest;
import com.project.petService.dtos.response.products.AttributeTypeResponse;
import com.project.petService.entities.AttributeType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AttributeTypeMapper {
    AttributeType toAttributeType(AttributeTypeRequest request);

    AttributeTypeResponse toAttributeTypeResponse(AttributeType attributeType);

    void updateAttributeType(@MappingTarget AttributeType attributeType, AttributeTypeRequest request);
}
