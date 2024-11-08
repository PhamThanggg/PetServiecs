package com.project.petService.controllers;

import com.project.petService.dtos.requests.products.SubCategoryRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.products.SubCategoryResponse;
import com.project.petService.services.products.SubCategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/subCategory")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class SubCategoryController {
    SubCategoryService categoryService;

    @PostMapping("")
    public ApiResponse<SubCategoryResponse> create(
            @RequestBody @Valid SubCategoryRequest request
    ){
        return ApiResponse.<SubCategoryResponse>builder()
                .message("Create successfully")
                .result(categoryService.createSubCategory(request))
                .build();
    }

    @GetMapping("")
    public ApiResponse<List<SubCategoryResponse>> getAllSubCategory(
    ){
        return ApiResponse.<List<SubCategoryResponse>>builder()
                .result(categoryService.getSubCategoryALl())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SubCategoryResponse> updateSubCategoryById(
            @PathVariable("id") Long id,
            @RequestBody @Valid SubCategoryRequest request
    ){
        return ApiResponse.<SubCategoryResponse>builder()
                .result(categoryService.updateSubCategory(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSubCategoryById(@PathVariable("id") Long id){
        categoryService.deleteSubCategory(id);
        return ApiResponse.<String>builder()
                .result("SubCategory has been deleted")
                .build();
    }
}
