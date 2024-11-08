package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.SubCategoryRequest;
import com.project.petService.dtos.response.products.SubCategoryResponse;
import com.project.petService.entities.Category;
import com.project.petService.entities.SubCategory;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.SubCategoryMapper;
import com.project.petService.repositories.CategoryRepository;
import com.project.petService.repositories.SubCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubCategoryService  {
    SubCategoryRepository subCategoryRepository;
    CategoryRepository categoryRepository;
    SubCategoryMapper subCategoryMapper;

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public SubCategoryResponse createSubCategory(SubCategoryRequest request) {
        Category category =categoryRepository.findById(request.getCategoryId()).
                orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));
        if(subCategoryRepository.existsByNameAndCategoryId(request.getName(), request.getCategoryId())){
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }

        SubCategory subCategory = subCategoryMapper.toSubCategory(request);
        subCategory.setCategory(category);
        return subCategoryMapper.toSubCategoryResponse(subCategoryRepository.save(subCategory));
    }

    public List<SubCategoryResponse> getSubCategoryALl() {
        return subCategoryRepository.findAll().stream().map(subCategoryMapper::toSubCategoryResponse).toList();
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public SubCategoryResponse updateSubCategory(SubCategoryRequest request, Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));
        Category category =categoryRepository.findById(request.getCategoryId()).
                orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        if(!subCategory.getName().equals(request.getName())){
            if(subCategoryRepository.existsByNameAndCategoryId(request.getName(), request.getCategoryId())){
                throw new AppException(ErrorCode.CATEGORY_EXISTS);
            }
        }

        subCategoryMapper.updateSubCategory(subCategory, request);
        subCategory.setCategory(category);
        return subCategoryMapper.toSubCategoryResponse(subCategoryRepository.save(subCategory));
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public void deleteSubCategory(Long id) {
        subCategoryRepository.deleteById(id);
    }
}
