package com.project.petService.mappers;


import com.project.petService.dtos.requests.products.SizeRequest;
import com.project.petService.dtos.response.products.SizeResponse;
import com.project.petService.entities.Size;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    Size toSize(SizeRequest request);

    SizeResponse toSizeResponse(Size attribute);

    void updateSize(@MappingTarget Size attribute, SizeRequest request);
}
