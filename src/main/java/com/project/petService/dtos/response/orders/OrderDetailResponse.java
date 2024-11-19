package com.project.petService.dtos.response.orders;

import com.project.petService.dtos.response.products.AttributeSizeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    int quantity;
    Double price;
    AttributeSizeResponse attributeSize;
}
