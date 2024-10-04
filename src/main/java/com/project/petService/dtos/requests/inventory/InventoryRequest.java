package com.project.petService.dtos.requests.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequest {
    @NotNull(message = "QUANTITY_NOT_NULL")
    @Min(value = 1, message = "QUANTITY_INVALID")
    int quantity;

    @NotNull(message = "BUSINESS_NOT_BLANK")
    @Min(value = 1, message = "BUSINESS_INVALID")
    Long businessId;

    @NotNull(message = "PRODUCT_NOT_BLANK")
    @Min(value = 1, message = "BUSINESS_INVALID")
    Long productId;
}
