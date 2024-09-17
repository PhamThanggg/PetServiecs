package com.project.petService.mappers;


import com.project.petService.dtos.requests.areas.AreaRequest;
import com.project.petService.dtos.response.areas.AreaResponse;
import com.project.petService.entities.Area;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AreaMapper {
    Area toArea(AreaRequest request);

    AreaResponse toAreaResponse(Area area);

    void updateArea(@MappingTarget Area area, AreaRequest request);
}
