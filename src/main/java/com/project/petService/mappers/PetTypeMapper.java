package com.project.petService.mappers;

import com.project.petService.dtos.requests.pets.PetTypeRequest;
import com.project.petService.dtos.response.pets.PetTypeResponse;
import com.project.petService.entities.PetType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PetTypeMapper {
    PetType toPet(PetTypeRequest request);

    PetTypeResponse toPetResponse(PetType pet);

    void updatePetType(@MappingTarget PetType pet, PetTypeRequest request);
}
