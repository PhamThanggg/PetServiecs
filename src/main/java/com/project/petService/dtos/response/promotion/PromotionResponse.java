package com.project.petService.dtos.response.promotion;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionResponse {
    String id;

    String name;

    String description;

    int discount;

    int count;

    LocalDate startDate;

    LocalDate endDate;
}
