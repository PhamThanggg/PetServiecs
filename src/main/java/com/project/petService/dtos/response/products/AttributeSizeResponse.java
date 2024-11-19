package com.project.petService.dtos.response.products;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeSizeResponse {
    Long id;

    Long price;

    int quantity;

    int quantitySold;

    SizeResponse size;

    AttributeOrderResponse attribute;
}
