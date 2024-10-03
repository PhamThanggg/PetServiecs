package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.ProductRequest;
import com.project.petService.dtos.response.products.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface IProductService {
    ProductResponse createProduct(ProductRequest request);

    ProductResponse findById(Long id);

    Page<ProductResponse> getProductALl(int page, int limit);

    Page<ProductResponse> searchProductOrCategoryOrPrice(String name, Long categoryId, Double price, int page, int limit);

    ProductResponse updateProduct(ProductRequest request, Long id);

    void deleteProduct(Long id);
}
