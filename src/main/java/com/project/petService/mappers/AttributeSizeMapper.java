package com.project.petService.mappers;


import com.project.petService.dtos.requests.products.AttributeSizeRequest;
import com.project.petService.dtos.response.products.AttributeSizeResponse;
import com.project.petService.entities.AttributeSize;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AttributeSizeMapper {
    AttributeSize toAttributeSize(AttributeSizeRequest request);

    AttributeSizeResponse toAttributeSizeResponse(AttributeSize attributeSize);

    List<AttributeSizeResponse> toAttributeSizeResponse(List<AttributeSize> attributeSizes);

    void updateAttributeSize(@MappingTarget AttributeSize attributeSize, AttributeSizeRequest request);
}
