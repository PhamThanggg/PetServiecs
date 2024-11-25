package com.project.petService.controllers;

import com.project.petService.dtos.requests.products.CategoryRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.products.CategoryResponse;
import com.project.petService.services.products.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/category")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> create(
            @RequestBody @Valid CategoryRequest request
    ){
        return ApiResponse.<CategoryResponse>builder()
                .message("Create successfully")
                .result(categoryService.createCategory(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategory(
    ){
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getCategoryALl())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategoryById(
            @PathVariable("id") Long id,
            @RequestBody @Valid CategoryRequest request
    ){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategoryById(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
                .result("Category has been deleted")
                .build();
    }
}
