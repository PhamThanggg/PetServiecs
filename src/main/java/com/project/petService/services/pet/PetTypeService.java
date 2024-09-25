package com.project.petService.services.pet;

import com.project.petService.dtos.requests.pets.PetTypeRequest;
import com.project.petService.dtos.response.pets.PetTypeResponse;
import com.project.petService.entities.PetType;
import com.project.petService.mappers.PetTypeMapper;
import com.project.petService.repositories.PetTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PetTypeService implements IPetTypeService {
    PetTypeRepository petRepository;
    PetTypeMapper petMapper;

    @Override
    public PetTypeResponse createPetType(PetTypeRequest request) {
        PetType pet = petMapper.toPet(request);
        return petMapper.toPetResponse(petRepository.save(pet));
    }

    @Override
    public List<PetTypeResponse> getAllPetType() {
        return petRepository.findAll().stream().map(petMapper::toPetResponse).toList();
    }

    @Override
    public PetTypeResponse updatePetType(PetTypeRequest request, Long id) {
        PetType pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not exists"));

        petMapper.updatePetType(pet, request);
        return petMapper.toPetResponse(petRepository.save(pet));
    }

    @Override
    public void deletePetType(Long id) {
        petRepository.deleteById(id);
    }
}
