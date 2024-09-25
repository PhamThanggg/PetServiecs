package com.project.petService.mappers;

import com.project.petService.dtos.requests.pets.PetRequest;
import com.project.petService.dtos.response.pets.PetResponse;
import com.project.petService.entities.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PetsMapper {
    Pet toPet(PetRequest request);

    @Mapping(source = "petType.id", target = "petTypeId")
    PetResponse toPetResponse(Pet pet);

    void updatePet(@MappingTarget Pet pet, PetRequest request);
}
