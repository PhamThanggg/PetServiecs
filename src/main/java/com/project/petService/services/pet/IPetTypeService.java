package com.project.petService.services.pet;

import com.project.petService.dtos.requests.pets.PetTypeRequest;
import com.project.petService.dtos.response.pets.PetTypeResponse;

import java.util.List;

public interface IPetTypeService {
    PetTypeResponse createPetType(PetTypeRequest request);

    List<PetTypeResponse> getAllPetType();

    PetTypeResponse updatePetType(PetTypeRequest request, Long id);

    void deletePetType(Long id);
}
