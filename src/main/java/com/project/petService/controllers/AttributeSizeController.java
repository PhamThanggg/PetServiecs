package com.project.petService.controllers;

import com.project.petService.dtos.requests.products.AttributeSizeRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.products.AttributeSizeResponse;
import com.project.petService.services.products.AttributeSizeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/attribute-size")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class AttributeSizeController {
    AttributeSizeService attributeSizeService;

    @PostMapping("")
    public ApiResponse<AttributeSizeResponse> create(
            @RequestBody @Valid AttributeSizeRequest request
    ){
        return ApiResponse.<AttributeSizeResponse>builder()
                .message("Create successfully")
                .result(attributeSizeService.createAttributeSize(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AttributeSizeResponse> updateAttributeSizeById(
            @PathVariable("id") Long id,
            @RequestBody @Valid AttributeSizeRequest request
    ){
        return ApiResponse.<AttributeSizeResponse>builder()
                .result(attributeSizeService.updateAttributeSize(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAttributeSizeById(@PathVariable("id") Long id){
        attributeSizeService.deleteAttributeSize(id);
        return ApiResponse.<String>builder()
                .result("AttributeSize has been deleted")
                .build();
    }
}
