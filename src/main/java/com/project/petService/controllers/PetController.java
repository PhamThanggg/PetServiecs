package com.project.petService.controllers;

import com.project.petService.dtos.requests.pets.PetRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.pets.PetResponse;
import com.project.petService.services.pet.PetService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/pet")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PetController {
    PetService petService;

    @PostMapping
    public ApiResponse<PetResponse> create(@RequestBody @Valid PetRequest request) {
        return ApiResponse.<PetResponse>builder()
                .message("Create successfully")
                .result(petService.createPet(request)).build();
    }

    @GetMapping
    public ApiResponse<List<PetResponse>> getAll() {
        return ApiResponse.<List<PetResponse>>builder()
                .result(petService.getAllPet()).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PetResponse> updateById(@PathVariable("id") Long id, @RequestBody @Valid PetRequest request) {
        return ApiResponse.<PetResponse>builder()
                .result(petService.updatePet(request, id)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable("id") Long id) {
        petService.deletePet(id);
        return ApiResponse.<String>builder()
                .result("Pet has been deleted").build();
    }
}
