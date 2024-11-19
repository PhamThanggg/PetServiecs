package com.project.petService.dtos.requests.promotion;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionRequest {
    String name;

    String description;

    int discount;

    int count;

    LocalDate startDate;

    LocalDate endDate;
}
