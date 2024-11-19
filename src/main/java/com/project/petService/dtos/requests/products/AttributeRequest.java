package com.project.petService.dtos.requests.products;

import lombok.*;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeRequest {
    String name;

    Long price;

    int percentDiscount;

    Long productId;

    Long attributeTypeId;

    Set<AttributeSizeRequest> attributeSizes;
}
