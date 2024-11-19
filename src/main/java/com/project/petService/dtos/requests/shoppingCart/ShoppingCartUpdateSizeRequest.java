package com.project.petService.dtos.requests.shoppingCart;


import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartUpdateSizeRequest {
    @Min(value = 1)
    Long attributeSizeId;
}
