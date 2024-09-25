package com.project.petService.mappers;

import com.project.petService.dtos.requests.service.ServiceRequest;
import com.project.petService.dtos.response.service.ServiceResponse;
import com.project.petService.entities.Services;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    Services toService(ServiceRequest request);

    ServiceResponse toServiceResponse(Services services);

    void updateService(@MappingTarget Services services, ServiceRequest request);
}
