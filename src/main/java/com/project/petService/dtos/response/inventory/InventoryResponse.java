package com.project.petService.dtos.response.inventory;

import com.project.petService.dtos.response.products.ProductResponse;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    Long id;

    Long businessId;

    int quantity;

    int quantitySold;

    ProductResponse product;
}
