package com.project.petService.dtos.requests.shoppingCart;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartRequest {
    @NotNull(message = "QUANTITY_NOT_NULL")
    @Min(value = 1, message = "QUANTITY_INVALID")
    Long quantity;

    @Positive(message = "TOTAL_INVALID")
    Double totalPrice;

    @NotBlank(message = "USER_NOT_NULL")
    @Size(min = 2,max = 500, message = "USER_INVALID")
    String userId;

    @NotNull(message = "PRODUCT_NOT_BLANK")
    @Min(value = 1, message = "PRODUCT_ID_INVALID")
    Long productId;
}
