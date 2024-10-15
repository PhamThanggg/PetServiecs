package com.project.petService.services.pet;

import com.project.petService.dtos.requests.pets.PetRequest;
import com.project.petService.dtos.response.pets.PetResponse;
import com.project.petService.entities.Pet;
import com.project.petService.mappers.PetsMapper;
import com.project.petService.repositories.PetsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PetService implements IPetService {
    PetsRepository petRepository;
    PetsMapper petMapper;

    @Override
    public PetResponse createPet(PetRequest request) {
        Pet pet = petMapper.toPet(request);
        return petMapper.toPetResponse(petRepository.save(pet));
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_PET')")
    public List<PetResponse> getAllPet() {
        return petRepository.findAll().stream().map(petMapper::toPetResponse).toList();
    }

    @Override
    public PetResponse updatePet(PetRequest request, Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not exists"));

        petMapper.updatePet(pet, request);
        return petMapper.toPetResponse(petRepository.save(pet));
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_PET')")
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
