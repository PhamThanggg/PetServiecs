package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.ProductRequest;
import com.project.petService.dtos.response.products.ProductDetailResponse;
import com.project.petService.dtos.response.products.ProductImageResponse;
import com.project.petService.dtos.response.products.ProductResponse;
import com.project.petService.entities.Category;
import com.project.petService.entities.Product;
import com.project.petService.entities.ProductImage;
import com.project.petService.entities.SubCategory;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.ProductImageMapper;
import com.project.petService.mappers.ProductMapper;
import com.project.petService.repositories.CategoryRepository;
import com.project.petService.repositories.ProductImageRepository;
import com.project.petService.repositories.ProductRepository;
import com.project.petService.repositories.SubCategoryRepository;
import com.project.petService.services.util.CloudService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService implements IProductService {
    ProductRepository productRepository;
    SubCategoryRepository categoryRepository;
    ProductImageRepository productImageRepository;
    ProductMapper productMapper;
    ProductImageMapper productImageMapper;
    CloudService cloudService;
    private int MAXIMUM_IMAGES_PER_MOVIE = 5;
    @Override
    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public ProductResponse createProduct(ProductRequest request) {
        if(productRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.PRODUCT_EXISTS);
        }
        SubCategory category =  categoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        Product product = productMapper.toProduct(request);
        product.setSubCategory(category);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public ProductDetailResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        return productMapper.toProductDetailResponse(product);
    }

    @Override
    public Page<ProductResponse> getProductALl(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return productRepository.findAll(pageable).map(productMapper::toProductResponse);
    }

    @Override
    public Page<ProductResponse> searchProductOrCategoryOrPrice(
            String name, Long categoryId,
            Double minPrice, Double maxPrice,
            String status,
            int page, int limit
    ) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));
        return productRepository.findProductOrCategoryOrPrice(name, categoryId, minPrice, maxPrice, status,     pageable).map(productMapper::toProductResponse);
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public ProductResponse createProductImage(Long productId, List<MultipartFile> files) throws IOException {
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= MAXIMUM_IMAGES_PER_MOVIE){
            throw new RuntimeException("Số lượng ảnh của 1 sản phẩm phải < " + MAXIMUM_IMAGES_PER_MOVIE);
        }

        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id " + productId));

        List<Map> listPathImage = cloudService.uploadFiles(files);


        Set<ProductImage> productImages = listPathImage.stream()
                .map(list -> ProductImage.builder()
                        .imageUrl(list.get("url").toString())
                        .product(existingProduct)
                        .build())
                .collect(Collectors.toSet());

        productImageRepository.saveAll(productImages);

        Set<ProductImageResponse> productImageResponses = productImages.stream()
                .map(productImageMapper::toProductImageResponse)
                .collect(Collectors.toSet());

        ProductResponse productResponse = productMapper.toProductResponse(existingProduct);
        productResponse.setProductImages(productImageResponses);
        return productResponse;
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public void deleteMovieImage(Set<String> productId) throws IOException {
        Set<ProductImage> movieImages = productImageRepository.findByIdIn(productId);
        if(movieImages.isEmpty())
            throw new RuntimeException("Không có ảnh nào tồn tại");

        Set<Long> imageId = new HashSet<>();
        for (ProductImage list : movieImages){
            String publicId = getPublicId(list.getImageUrl());
            if(publicId != null){
                cloudService.deleteImage(publicId);
            }
            imageId.add(list.getId());
        }

        productImageRepository.deleteByIdIn(imageId);
    }


    @Override
    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public ProductResponse updateProduct(ProductRequest request, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        if(!product.getName().equals(request.getName())){
            if(productRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.PRODUCT_EXISTS);
            }
        }
        SubCategory category =  categoryRepository.findById(request.getSubCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        product.setSubCategory(category);
        productMapper.updateProduct(product, request);
        product.setSubCategory(category);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private String getPublicId(String imagePath){
        if (imagePath == null || !imagePath.contains("/")) {
            throw new RuntimeException("Invalid path");
        }

        String[] pathSegments = imagePath.split("/");

        if (pathSegments.length > 0) {
            String publicIdWithFormat = pathSegments[pathSegments.length-1];
            String[] publicIdSegments = publicIdWithFormat.split("\\.");
            return publicIdSegments[0];
        }

        return null;
    }
}
