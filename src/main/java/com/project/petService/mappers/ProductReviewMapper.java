package com.project.petService.mappers;


import com.project.petService.dtos.requests.products.ProductReviewRequest;
import com.project.petService.dtos.response.products.ProductReviewResponse;
import com.project.petService.entities.ProductReview;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductReviewMapper {
    ProductReview toProductReview(ProductReviewRequest request);

    ProductReviewResponse toProductReviewResponse(ProductReview area);

    void updateProductReview(@MappingTarget ProductReview productReview, ProductReviewRequest request);
}
