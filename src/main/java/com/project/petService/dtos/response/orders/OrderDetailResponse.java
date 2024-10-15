package com.project.petService.dtos.response.orders;

import com.project.petService.dtos.response.inventory.InventoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    int quantity;
    Double price;
    Set<InventoryResponse> inventoryResponses;
}
