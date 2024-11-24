package com.project.petService.controllers;

import com.project.petService.dtos.requests.pets.PetRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.PageResponse;
import com.project.petService.dtos.response.pets.PetResponse;
import com.project.petService.services.pet.PetService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
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

    @GetMapping("/get")
    public ApiResponse<PetResponse> getPet(@RequestParam Long id) {
        return ApiResponse.<PetResponse>builder()
                .result(petService.getPetById(id)).build();
    }

    @PutMapping
    public ApiResponse<PetResponse> updateById(@RequestParam("id") Long id, @RequestBody @Valid PetRequest request) {
        return ApiResponse.<PetResponse>builder()
                .result(petService.updatePet(request, id)).build();
    }

    @DeleteMapping
    public ApiResponse<String> deleteById(@RequestParam("id") Long id) {
        petService.deletePet(id);
        return ApiResponse.<String>builder()
                .result("Pet has been deleted").build();
    }

    @GetMapping("/search")
    public PageResponse<List<PetResponse>> searchAll(@RequestParam("page") int page,
                                                     @RequestParam(name = "name", required = false) String name,
                                                     @RequestParam(name = "petType", required = false) Long type,
                                                     @RequestParam("limit") int limit) {
        Page<PetResponse> petResponses = petService.getSearchPet(page, limit, name, type);
        return PageResponse.<List<PetResponse>>builder()
                .currentPage(petResponses.getNumber())
                .totalPages(petResponses.getTotalPages())
                .totalElements(petResponses.getTotalElements())
                .pageSize(petResponses.getSize())
                .result(petResponses.getContent())
                .build();
    }
}
