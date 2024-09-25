package com.project.petService.controllers;

import com.project.petService.dtos.requests.pets.PetTypeRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.pets.PetTypeResponse;
import com.project.petService.services.pet.PetTypeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/pet_type")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PetTypeController {
    PetTypeService petService;

    @PostMapping
    public ApiResponse<PetTypeResponse> create(@RequestBody @Valid PetTypeRequest request) {
        return ApiResponse.<PetTypeResponse>builder()
                .message("Create successfully")
                .result(petService.createPetType(request)).build();
    }

    @GetMapping
    public ApiResponse<List<PetTypeResponse>> getAll() {
        return ApiResponse.<List<PetTypeResponse>>builder()
                .result(petService.getAllPetType()).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PetTypeResponse> updateById(@PathVariable("id") Long id, @RequestBody @Valid PetTypeRequest request) {
        return ApiResponse.<PetTypeResponse>builder()
                .result(petService.updatePetType(request, id)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable("id") Long id) {
        petService.deletePetType(id);
        return ApiResponse.<String>builder()
                .result("Pet type has been deleted").build();
    }
}
