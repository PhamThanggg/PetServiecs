package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.CategoryRequest;
import com.project.petService.dtos.response.products.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getCategoryALl();

    CategoryResponse updateCategory(CategoryRequest request, Long id);

    void deleteCategory(Long id);
}
