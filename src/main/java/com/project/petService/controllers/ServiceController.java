package com.project.petService.controllers;

import com.project.petService.dtos.requests.service.ServiceRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.service.ServiceResponse;
import com.project.petService.services.service.SerService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/service")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ServiceController {
    SerService serService;

    @PostMapping
    public ApiResponse<ServiceResponse> create(@RequestBody @Valid ServiceRequest request) {
        return ApiResponse.<ServiceResponse>builder()
                .message("Create successfully")
                .result(serService.create(request)).build();
    }

    @GetMapping
    public ApiResponse<List<ServiceResponse>> getAll() {
        return ApiResponse.<List<ServiceResponse>>builder()
                .result(serService.getAll()).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ServiceResponse> updateById(@PathVariable("id") Long id, @RequestBody @Valid ServiceRequest request) {
        return ApiResponse.<ServiceResponse>builder()
                .result(serService.update(request, id)).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable("id") Long id) {
        serService.delete(id);
        return ApiResponse.<String>builder()
                .result("Service has been deleted").build();
    }
}
