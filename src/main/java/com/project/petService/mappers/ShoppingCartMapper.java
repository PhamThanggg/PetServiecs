package com.project.petService.mappers;


import com.project.petService.dtos.requests.shoppingCart.ShoppingCartRequest;
import com.project.petService.dtos.response.shoppingCart.ShoppingCartResponse;
import com.project.petService.entities.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {
    ShoppingCart toShoppingCart(ShoppingCartRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    ShoppingCartResponse toShoppingCartResponse(ShoppingCart product);

    void updateShoppingCart(@MappingTarget ShoppingCart shoppingCart, ShoppingCartRequest request);

}
