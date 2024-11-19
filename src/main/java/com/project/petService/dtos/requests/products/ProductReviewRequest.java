package com.project.petService.dtos.requests.products;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReviewRequest {
    Float rating;
    String comment;
    Long productId;
    Long orderId;
}
