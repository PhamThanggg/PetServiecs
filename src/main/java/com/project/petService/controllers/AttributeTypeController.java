package com.project.petService.controllers;

import com.project.petService.dtos.requests.products.AttributeTypeRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.products.AttributeTypeResponse;
import com.project.petService.services.products.AttributeTypeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/attributeType")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class AttributeTypeController {
    AttributeTypeService attributeTypeService;

    @PostMapping("")
    public ApiResponse<AttributeTypeResponse> create(
            @RequestBody @Valid AttributeTypeRequest request
    ){
        return ApiResponse.<AttributeTypeResponse>builder()
                .message("Create successfully")
                .result(attributeTypeService.createAttributeType(request))
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<AttributeTypeResponse>> getAllAttributeType(
    ){
        return ApiResponse.<List<AttributeTypeResponse>>builder()
                .result(attributeTypeService.getAttributeTypeALl())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AttributeTypeResponse> updateAttributeTypeById(
            @PathVariable("id") Long id,
            @RequestBody @Valid AttributeTypeRequest request
    ){
        return ApiResponse.<AttributeTypeResponse>builder()
                .result(attributeTypeService.updateAttributeType(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAttributeTypeById(@PathVariable("id") Long id){
        attributeTypeService.deleteAttributeType(id);
        return ApiResponse.<String>builder()
                .result("AttributeType has been deleted")
                .build();
    }
}
