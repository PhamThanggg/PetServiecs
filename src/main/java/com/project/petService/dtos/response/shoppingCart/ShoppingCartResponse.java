package com.project.petService.dtos.response.shoppingCart;


import com.project.petService.dtos.response.products.ProductCartResponse;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartResponse {
    Long quantity;

    Double totalPrice;

    String userId;

    ProductCartResponse product;
}
