package com.project.petService.services.pet;

import com.project.petService.dtos.requests.booking.BookingRequest;
import com.project.petService.dtos.requests.pets.PetRequest;
import com.project.petService.dtos.response.booking.BookingResponse;
import com.project.petService.dtos.response.pets.PetResponse;

import java.util.List;

public interface IPetService {
    PetResponse createPet(PetRequest request);

    List<PetResponse> getAllPet();

    PetResponse updatePet(PetRequest request, Long id);

    void deletePet(Long id);
}
