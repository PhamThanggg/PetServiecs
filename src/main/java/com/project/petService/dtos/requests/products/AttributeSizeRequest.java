package com.project.petService.dtos.requests.products;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeSizeRequest {
    Long price;

    int quantity;

    Long sizeId;
}
