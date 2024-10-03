package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.ProductRequest;
import com.project.petService.dtos.response.products.ProductResponse;
import com.project.petService.entities.Category;
import com.project.petService.entities.Product;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.ProductMapper;
import com.project.petService.repositories.CategoryRepository;
import com.project.petService.repositories.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService implements IProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if(productRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.PRODUCT_EXISTS);
        }
        Category category =  categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        Product product = productMapper.toProduct(request);
        product.setCategory(category);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        return productMapper.toProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getProductALl(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return productRepository.findAll(pageable).map(productMapper::toProductResponse);
    }

    @Override
    public Page<ProductResponse> searchProductOrCategoryOrPrice(
            String name, Long categoryId, Double price,
            int page, int limit
    ) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
        return productRepository.findProductOrCategoryOrPrice(name, categoryId, price, pageable).map(productMapper::toProductResponse);
    }


    @Override
    public ProductResponse updateProduct(ProductRequest request, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        if(!product.getName().equals(request.getName())){
            if(productRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.PRODUCT_EXISTS);
            }
        }
        Category category =  categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        product.setCategory(category);
        productMapper.updateProduct(product, request);
        product.setCategory(category);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
