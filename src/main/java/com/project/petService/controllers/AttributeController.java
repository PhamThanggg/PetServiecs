package com.project.petService.controllers;

import com.project.petService.dtos.requests.products.AttributeRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.products.AttributeResponse;
import com.project.petService.services.products.AttributeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/attribute")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class AttributeController {
    AttributeService attributeService;

    @PostMapping("")
    public ApiResponse<AttributeResponse> create(
            @RequestBody @Valid AttributeRequest request
    ){
        return ApiResponse.<AttributeResponse>builder()
                .message("Create successfully")
                .result(attributeService.createAttribute(request))
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<AttributeResponse>> getAllAttribute(
            @RequestParam("productId") Long productId
    ){
        return ApiResponse.<List<AttributeResponse>>builder()
                .result(attributeService.getAttributeALl(productId))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AttributeResponse> updateAttributeById(
            @PathVariable("id") Long id,
            @RequestBody @Valid AttributeRequest request
    ){
        return ApiResponse.<AttributeResponse>builder()
                .result(attributeService.updateAttribute(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAttributeById(@PathVariable("id") Long id){
        attributeService.deleteAttribute(id);
        return ApiResponse.<String>builder()
                .result("Attribute has been deleted")
                .build();
    }
}
