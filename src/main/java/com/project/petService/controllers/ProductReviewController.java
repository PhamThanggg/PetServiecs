package com.project.petService.controllers;

import com.project.petService.dtos.requests.products.ProductReviewRequest;
import com.project.petService.dtos.response.ApiResponse;

import com.project.petService.dtos.response.PageResponse;
import com.project.petService.dtos.response.products.ProductReviewResponse;
import com.project.petService.dtos.response.products.RatingCountResponse;
import com.project.petService.entities.User;
import com.project.petService.services.products.ProductReviewService;
import com.project.petService.services.user.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/product-review")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ProductReviewController {
    ProductReviewService productReviewService;
    UserService userService;

    @PostMapping("")
    public ApiResponse<ProductReviewResponse> create(
            @RequestBody @Valid ProductReviewRequest request
    ){
        User user = userService.getMyUserInfo();
        return ApiResponse.<ProductReviewResponse>builder()
                .message("Create successfully")
                .result(productReviewService.createProductReview(request, user.getId(), user))
                .build();
    }

    @GetMapping("")
    public PageResponse<List<ProductReviewResponse>> getAllProductReview(
            @RequestParam("productId") Long productId,
            @RequestParam(value = "rating", required = false) Float rating,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        Page<ProductReviewResponse> responses = productReviewService.getProductReviewALl(productId, rating, page, limit);
        return PageResponse.<List<ProductReviewResponse>>builder()
                .currentPage(responses.getNumber())
                .totalPages(responses.getTotalPages())
                .totalElements(responses.getTotalElements())
                .pageSize(responses.getSize())
                .result(responses.getContent())
                .build();
    }

    @GetMapping("/rating")
    public ApiResponse<RatingCountResponse> getAllProductReviewRating(
            @RequestParam("productId") Long productId
    ){
        return ApiResponse.<RatingCountResponse>builder()
                .result(productReviewService.getRatingCount(productId))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductReviewResponse> updateProductReviewById(
            @PathVariable("id") Long id,
            @RequestBody @Valid ProductReviewRequest request
    ){
        return ApiResponse.<ProductReviewResponse>builder()
                .result(null)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProductReviewById(@PathVariable("id") Long id){
        productReviewService.deleteProductReview(id);
        return ApiResponse.<String>builder()
                .result("ProductReview has been deleted")
                .build();
    }
}
