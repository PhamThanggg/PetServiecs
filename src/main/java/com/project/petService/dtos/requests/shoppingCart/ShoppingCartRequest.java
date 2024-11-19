package com.project.petService.dtos.requests.shoppingCart;


import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartRequest {
    @NotNull(message = "QUANTITY_NOT_NULL")
    @Min(value = 1, message = "QUANTITY_INVALID")
    Long quantity;

    @Min(value = 1)
    Long attributeSizeId;

    @NotNull(message = "PRODUCT_NOT_BLANK")
    @Min(value = 1, message = "PRODUCT_ID_INVALID")
    Long productId;
}
