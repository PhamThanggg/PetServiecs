package com.project.petService.repositories;

import com.project.petService.entities.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    boolean existsByUserIdAndProductIdAndOrderId(String userId, Long productId, Long orderId);
    @Query("SELECT pr FROM ProductReview pr " +
            "WHERE pr.product.id = :productId " +
            "AND (:rating IS NULL OR pr.rating = :rating)")
    Page<ProductReview> findByProductIdQuery(
            @Param("productId") Long productId,
            @Param("rating") Float rating,
            Pageable pageable
    );

    List<ProductReview> findByProductId(Long productId);
}
