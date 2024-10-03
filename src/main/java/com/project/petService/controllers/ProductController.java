package com.project.petService.controllers;

import com.project.petService.dtos.requests.products.ProductRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.products.ProductResponse;
import com.project.petService.services.products.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/product")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ProductController {
    ProductService productService;

    @PostMapping("")
    public ApiResponse<ProductResponse> create(
            @RequestBody @Valid ProductRequest request
            ) throws IOException {
        ProductResponse productResponses = productService.createProduct(request);
        return ApiResponse.<ProductResponse>builder()
                .message("Tạo sản phẩm thành công")
                .result(productResponses)
                .build();
    }

    @PostMapping(value = "upload_images/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute List<MultipartFile> files
    ) throws IOException {
        return ApiResponse.<ProductResponse>builder()
                .message("Upload ảnh thành công")
                .result(productService.createProductImage(productId, files))
                .build();
    }


    @GetMapping("")
    public ApiResponse<List<ProductResponse>> getAllProduct(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<ProductResponse> productResponses = productService
                .getProductALl(page, limit)
                .getContent();
        int totalCinema = productResponses.size();
        return ApiResponse.<List<ProductResponse>>builder()
                .message("Tổng số sản phẩm: " + totalCinema)
                .result(productResponses)
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> searchProduct(
            @RequestParam(value = "nameProduct", required = false) String nameProduct,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<ProductResponse> productResponse = productService
                .searchProductOrCategoryOrPrice(nameProduct, categoryId, price,  page, limit)
                .getContent();
        return ApiResponse.<List<ProductResponse>> builder()
                .result(productResponse)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable("id") Long id){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.findById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProductById(
            @PathVariable("id") Long id,
            @RequestBody @Valid ProductRequest request
    ){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProductById(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return ApiResponse.<String>builder()
                .result("Xóa sản phẩm thành công")
                .build();
    }

    @DeleteMapping("deleteImage/{id}")
    public ApiResponse<String> deleteImageById(@PathVariable("id") Set<String> ids) throws IOException {
        productService.deleteMovieImage(ids);
        return ApiResponse.<String>builder()
                .result("Xóa ảnh thành công")
                .build();
    }

}
