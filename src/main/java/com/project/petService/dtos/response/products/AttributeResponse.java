package com.project.petService.dtos.response.products;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeResponse {
    Long id;

    String name;

    Long price;

    int percentDiscount;

    Long productId;

    AttributeTypeResponse attributeType;

    Set<AttributeSizeResponse> attributeSize;
}
