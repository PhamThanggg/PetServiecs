package com.project.petService.controllers;

import com.project.petService.dtos.requests.products.SizeRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.products.SizeResponse;
import com.project.petService.services.products.SizeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/size")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class SizeController {
    SizeService sizeService;

    @PostMapping("")
    public ApiResponse<SizeResponse> create(
            @RequestBody @Valid SizeRequest request
    ){
        return ApiResponse.<SizeResponse>builder()
                .message("Create successfully")
                .result(sizeService.createSize(request))
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<SizeResponse>> getAllSize(
    ){
        return ApiResponse.<List<SizeResponse>>builder()
                .result(sizeService.getSizeALl())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SizeResponse> updateSizeById(
            @PathVariable("id") Long id,
            @RequestBody @Valid SizeRequest request
    ){
        return ApiResponse.<SizeResponse>builder()
                .result(sizeService.updateSize(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSizeById(@PathVariable("id") Long id){
        sizeService.deleteSize(id);
        return ApiResponse.<String>builder()
                .result("Size has been deleted")
                .build();
    }
}
