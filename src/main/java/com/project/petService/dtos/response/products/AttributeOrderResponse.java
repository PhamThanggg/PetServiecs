package com.project.petService.dtos.response.products;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeOrderResponse {
    Long id;

    String name;

    Long price;

    Long priceSell;

    int percentDiscount;

    ProductResponse product;

    AttributeTypeResponse attributeType;
}
