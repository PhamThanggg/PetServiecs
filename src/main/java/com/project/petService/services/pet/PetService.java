package com.project.petService.services.pet;

import com.project.petService.dtos.requests.pets.PetRequest;
import com.project.petService.dtos.response.pets.PetResponse;
import com.project.petService.entities.Pet;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.PetsMapper;
import com.project.petService.repositories.PetsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<PetResponse> getSearchPet(int page, int limit, String name, Long type) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return petRepository.findByNameAndType(name, type, pageable).map(petMapper::toPetResponse);
    }

    public PetResponse getPetById(Long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PET_NOT_EXISTS));
        return petMapper.toPetResponse(pet);
    }
}
