package com.project.petService.controllers;

import com.project.petService.dtos.requests.areas.AreaRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.areas.AreaResponse;
import com.project.petService.services.areas.AreaService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/area")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class AreaController {
    AreaService areaService;

    @PostMapping("")
    public ApiResponse<AreaResponse> create(
            @RequestBody @Valid AreaRequest request
    ){
        return ApiResponse.<AreaResponse>builder()
                .message("Create successfully")
                .result(areaService.createArea(request))
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<AreaResponse>> getAllArea(
    ){
        return ApiResponse.<List<AreaResponse>>builder()
                .result(areaService.getAreaALl())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AreaResponse> updateAreaById(
            @PathVariable("id") Long id,
            @RequestBody @Valid AreaRequest request
    ){
        return ApiResponse.<AreaResponse>builder()
                .result(areaService.updateArea(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAreaById(@PathVariable("id") Long id){
        areaService.deleteArea(id);
        return ApiResponse.<String>builder()
                .result("Area has been deleted")
                .build();
    }
}
