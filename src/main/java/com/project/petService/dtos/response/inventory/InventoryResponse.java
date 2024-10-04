package com.project.petService.dtos.response.inventory;

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

    Long productId;

    int quantity;

    int quantitySold;
}
