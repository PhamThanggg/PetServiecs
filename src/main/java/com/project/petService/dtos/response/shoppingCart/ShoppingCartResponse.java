package com.project.petService.dtos.response.shoppingCart;


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

    Long productId;
}
