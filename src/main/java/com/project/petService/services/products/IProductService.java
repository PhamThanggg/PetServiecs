package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.ProductRequest;
import com.project.petService.dtos.response.products.ProductDetailResponse;
import com.project.petService.dtos.response.products.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IProductService {
    ProductResponse createProduct(ProductRequest request);

    ProductDetailResponse findById(Long id);

    Page<ProductResponse> getProductALl(int page, int limit);

    Page<ProductResponse> searchProductOrCategoryOrPrice(String name, Long categoryId,
                                                         Double minPrice, Double maxPrice,
                                                         String status,
                                                         int page, int limit);

    ProductResponse createProductImage(Long productId, List<MultipartFile> files) throws IOException;

    void deleteMovieImage(Set<String> movieId) throws IOException;

    ProductResponse updateProduct(ProductRequest request, Long id);

    void deleteProduct(Long id);
}
