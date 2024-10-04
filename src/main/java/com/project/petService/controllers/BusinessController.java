package com.project.petService.controllers;

import com.project.petService.dtos.requests.business.BusinessRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.business.BusinessResponse;
import com.project.petService.services.business.BusinessService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/business")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BusinessController {
    BusinessService businessService;

    @PostMapping("")
    public ApiResponse<BusinessResponse> create(@RequestBody @Valid BusinessRequest request) {
        return ApiResponse.<BusinessResponse>builder()
                .message("Tạo thành công")
                .result(businessService.createBusiness(request))
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<BusinessResponse>> getAllBusiness() {
        return ApiResponse.<List<BusinessResponse>>builder()
                .result(businessService.getBusinessALl())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BusinessResponse> updateBusinessById(@PathVariable("id") Long id,
                                                            @RequestBody @Valid BusinessRequest request) {
        return ApiResponse.<BusinessResponse>builder()
                .result(businessService.updateBusiness(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBusinessById(@PathVariable("id") Long id) {
        businessService.deleteBusiness(id);
        return ApiResponse.<String>builder()
                .result("Xóa thành công").build();
    }
}
