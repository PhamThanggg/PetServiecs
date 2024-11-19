package com.project.petService.dtos.response.products;

import com.project.petService.dtos.response.users.UserResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReviewResponse {
    Long id;

    Float rating;

    String comment;

    UserResponse user;
}
