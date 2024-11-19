package com.project.petService.dtos.response.products;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingCountResponse {
    Float rating;
    int countReview;
}
