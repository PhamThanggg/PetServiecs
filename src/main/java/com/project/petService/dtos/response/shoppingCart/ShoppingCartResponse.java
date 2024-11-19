package com.project.petService.dtos.response.shoppingCart;


import com.project.petService.dtos.response.products.ProductCartResponse;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartResponse {
    Long id;

    Long quantity;

    Double totalPrice;

    Long attributeSizeId;

    String userId;

    ProductCartResponse product;

    String message;
}
