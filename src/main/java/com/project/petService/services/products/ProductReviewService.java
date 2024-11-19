package com.project.petService.services.products;

import com.project.petService.dtos.requests.products.ProductReviewRequest;
import com.project.petService.dtos.response.products.ProductReviewResponse;
import com.project.petService.dtos.response.products.RatingCountResponse;
import com.project.petService.entities.Order;
import com.project.petService.entities.Product;
import com.project.petService.entities.ProductReview;
import com.project.petService.entities.User;
import com.project.petService.exceptions.AppException;
import com.project.petService.exceptions.ErrorCode;
import com.project.petService.mappers.ProductReviewMapper;
import com.project.petService.repositories.OrderRepository;
import com.project.petService.repositories.ProductRepository;
import com.project.petService.repositories.ProductReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductReviewService {
    ProductReviewRepository productReviewRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;
    ProductReviewMapper productReviewMapper;

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT') or hasRole('USER')")
    public ProductReviewResponse createProductReview(ProductReviewRequest request, String userId, User user) {
        if(productReviewRepository.existsByUserIdAndProductIdAndOrderId(userId, request.getProductId(), request.getOrderId())){
            throw new AppException(ErrorCode.PRODUCT_REVIEW_EXISTS);
        }
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTS));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS));

        ProductReview productReview = productReviewMapper.toProductReview(request);
        productReview.setProduct(product);
        productReview.setOrder(order);
        productReview.setUser(user);
        return productReviewMapper.toProductReviewResponse(productReviewRepository.save(productReview));
    }

    public Page<ProductReviewResponse> getProductReviewALl(Long productId, Float rating, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "id"));

        return productReviewRepository.findByProductIdQuery(productId, rating, pageable).map(productReviewMapper::toProductReviewResponse);
    }

    public RatingCountResponse getRatingCount(Long productId){
        List<ProductReview> reviews = productReviewRepository.findByProductId(productId);

        int countReview = reviews.size();

        Float totalRating = 0.0f;
        for (ProductReview review : reviews) {
            totalRating += review.getRating();
        }

        Float averageRating = countReview > 0 ? totalRating / countReview : 0.0f;

        return new RatingCountResponse(averageRating, countReview);
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public ProductReviewResponse updateProductReview(ProductReviewRequest request, Long id) {
        ProductReview productReview = productReviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTS));

        productReviewMapper.updateProductReview(productReview, request);
        return productReviewMapper.toProductReviewResponse(productReviewRepository.save(productReview));
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    public void deleteProductReview(Long id) {
        productReviewRepository.deleteById(id);
    }
}
