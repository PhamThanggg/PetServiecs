package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.CategoryRequest;
import com.project.petService.dtos.response.products.CategoryResponse;
import com.project.petService.entities.Category;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.CategoryMapper;
import com.project.petService.repositories.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public CategoryResponse createCategory(CategoryRequest request) {
        if(categoryRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }

        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponse> getCategoryALl() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public CategoryResponse updateCategory(CategoryRequest request, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        if(!category.getName().equals(request.getName())){
            if(categoryRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.CATEGORY_EXISTS);
            }
        }

        categoryMapper.updateCategory(category, request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
